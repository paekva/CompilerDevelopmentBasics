import rulesImplementation.Expression
import kotlin.collections.ArrayList

class SyntaxAnalyzer(private val lexemList: ArrayList<Lexem>) {

    private var currentLexem: Lexem = lexemList[0]
    private var currentLexemIndex: Int = 0
    private fun getCurrentLexeme() = currentLexem
    private fun moveToTheNextLexeme(): Lexem {
        getNextLexeme()
        return currentLexem
    }

    fun beginAnalise(): ASTNode? = program()

    // <Программа> ::= <Объявление переменных> <Описание вычислений>
    private fun program(): ASTNode? {
        val declareVariablesNode = declareVariables()
        val declareCalculationsNode = declareCalculations()

        val parent = constructTree(GrammarSymbols.PROGRAM, arrayListOf(declareVariablesNode, declareCalculationsNode))
        if(parent == null)
            printErrMsg("program")
        return parent
    }

    // <Объявление переменных> ::= Var <Список переменных>
    private fun declareVariables() : ASTNode? {
        val children: ArrayList<ASTNode?> = arrayListOf()

        if(currentLexem.type == LexemType.VAR){
            val varNode = addNewNodeToAST(GrammarSymbols.VAR, currentLexem)
            children.add(varNode)
            getNextLexeme()

            val variablesListNode = variablesList()
            children.add(variablesListNode)
        }

        val parent = constructTree(GrammarSymbols.DECLARE_VARIABLES, children)
        if(parent == null)
            printErrMsg("declareVariables")

        skipLineBreak()

        return parent
    }

    // <Описание вычислений> ::= Begin < Список операторов > End
    private fun declareCalculations() : ASTNode? {
        val children: ArrayList<ASTNode?> = arrayListOf()
        if(currentLexem.type == LexemType.BEGIN){
            val beginNode = addNewNodeToAST(GrammarSymbols.BEGIN, currentLexem)
            children.add(beginNode)
            getNextLexeme()
            skipLineBreak()

            val operatorsListNode = operatorsList()
            children.add(operatorsListNode)
            getNextLexeme()

            if(currentLexem.type == LexemType.END){
                val endNode = addNewNodeToAST(GrammarSymbols.END, currentLexem)
                children.add(endNode)
                getNextLexeme()
                skipLineBreak()
            }
        }

        val parent = constructTree(GrammarSymbols.DECLARE_CALCULATIONS, children)
        if(parent == null)
            printErrMsg("declareCalculations")

        return parent
    }

    // <Список переменных> ::= <Идент> | <Идент> , <Список переменных>
    private fun variablesList(): ASTNode?{
        val children: ArrayList<ASTNode?> = arrayListOf()
        if(currentLexem.type == LexemType.IDENTIFIER){
            val identifierNode = addNewNodeToAST(GrammarSymbols.IDENTIFIER, currentLexem)
            children.add(identifierNode)
            getNextLexeme()

            skipLineBreak()

            if(currentLexem.type == LexemType.COMMA){
                val commaNode = addNewNodeToAST(GrammarSymbols.COMMA, currentLexem)
                children.add(commaNode)

                getNextLexeme()
                val variablesListNode = variablesList()
                children.add(variablesListNode)
            }
        }

        val parent = constructTree(GrammarSymbols.VARIABLES_LIST, children)
        if(parent == null)
            printErrMsg("variablesList")
        return parent
    }

    //<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
    private fun operatorsList() : ASTNode? {
        val children: ArrayList<ASTNode?> = arrayListOf()

        val operatorNode = operator()
        children.add(operatorNode)
        getNextLexeme()

        skipLineBreak()
        val operatorsListNode = operatorsList()
        children.add(operatorsListNode)

        val parent = constructTree(GrammarSymbols.OPERATORS_LIST, children)
        if(parent == null)
            printErrMsg("operatorsList")
        return parent
    }

    // <Оператор>::=<Присваивание> |<Сложный оператор>
    private fun operator(): ASTNode? {
        val assignmentNode = assignment()
        val complexOperatorNode = complexOperator()
        val parent = constructTree(GrammarSymbols.OPERATOR, arrayListOf(assignmentNode, complexOperatorNode))

        if(parent == null)
            printErrMsg("operator")
        return parent
    }

    // <Присваивание> ::= <Идент> := <Выражение>
    private fun assignment(): ASTNode?{
        val children: ArrayList<ASTNode?> = arrayListOf()
        if(currentLexem.type == LexemType.IDENTIFIER){
            val identifierNode = addNewNodeToAST(GrammarSymbols.IDENTIFIER, currentLexem)
            children.add(identifierNode)
            getNextLexeme()

            if(currentLexem.type == LexemType.DECLARE){
                val assignmentSignNode = addNewNodeToAST(GrammarSymbols.ASSIGNMENT_SIGN, currentLexem)
                children.add(assignmentSignNode)
                getNextLexeme()

                val expressionNode = expression()
                children.add(expressionNode)
            }
        }

        val parent = constructTree(GrammarSymbols.ASSIGNMENT, children)
        if(parent == null)
            printErrMsg("assignment")
        return parent
    }

    // <Выражение> ::= <Ун.оп.> <Подвыражение> | <Подвыражение>
    private fun expression(): ASTNode? {
        return Expression(::getCurrentLexeme, ::moveToTheNextLexeme).analyze()
    }

    // <Сложный оператор> ::= IF < Выражение> THEN Оператор | IF <Выражение> THEN <Оператор> ELSE <Оператор>| <Составной оператор>
    private fun complexOperator() : ASTNode?{
        return null
    }

    private fun getNextLexeme(){
        currentLexem = lexemList[currentLexemIndex+1]
        currentLexemIndex++
    }

    private fun addNewNodeToAST(gs: GrammarSymbols, lexem: Lexem) : ASTNode? = ASTNode(gs, lexem)

    private fun skipLineBreak(): Boolean{
        if(currentLexem.type == LexemType.LINEBREAK){
            getNextLexeme()
            return true
        }
        return false
    }
    
    // <Составной оператор>::= Begin < Список операторов > End
    private fun compoundOperator(){
        
    }
}