import kotlin.collections.ArrayList

class SyntaxAnalyzer(private val lexemList: ArrayList<Lexem>) {

    private var currentLexem: Lexem = lexemList[0]
    private var currentLexemIndex: Int = 0
    private fun lookAheadType(): LexemType = if(currentLexemIndex < lexemList.size) lexemList[currentLexemIndex+1].type else LexemType.LINEBREAK
    private fun printErrMsg(ruleName: String) = println("problem constructing tree: $ruleName")

    fun beginAnalise(): ASTNode? = program()

    // <Программа> ::= <Объявление переменных> <Описание вычислений>
    private fun program(): ASTNode? {
        var parent: ASTNode? = null
        val declareVariablesNode = declareVariables()
        val declareCalculationsNode = declareCalculations()

        if(declareVariablesNode != null && declareCalculationsNode!=null)
            parent = constructTree(GrammarSymbols.PROGRAM, arrayListOf(declareVariablesNode, declareCalculationsNode))

        if(parent == null)
            printErrMsg("program")

        return parent
    }

    /*
<Ун.оп.> ::= "-"
<Бин.оп.> ::= "-" | "+" | "*" | "/" | ">>" | "<<" | ">" | "<" | "="
<Операнд> ::= <Идент> | <Const>
<Составной оператор>::= Begin < Список операторов > End*/

    // <Объявление переменных> ::= Var <Список переменных>
    private fun declareVariables() : ASTNode? {
        var parent: ASTNode? = null
        if(currentLexem.type == LexemType.VAR){
            val varNode = addNewNodeToAST(GrammarSymbols.VAR, currentLexem)
            getNextLexem()
            val variablesListNode = variablesList()

            if(varNode!=null && variablesListNode!=null)
                parent = constructTree(GrammarSymbols.DECLARE_VARIABLES, arrayListOf(varNode, variablesListNode))
        }

        if(parent == null)
            printErrMsg("declareVariables")
        return parent
    }

    // <Описание вычислений> ::= Begin < Список операторов > End
    private fun declareCalculations() : ASTNode? {
        if(currentLexem.type == LexemType.BEGIN){
            addNewNodeToAST(GrammarSymbols.BEGIN, currentLexem)
            operatorsList()
            if(currentLexem.type == LexemType.END){
                addNewNodeToAST(GrammarSymbols.END, currentLexem)
            }
        }
        return null
    }

    // <Список переменных> ::= <Идент> | <Идент> , <Список переменных>
    private fun variablesList(): ASTNode?{
        var parent: ASTNode? = null
        if(currentLexem.type == LexemType.IDENTIFIER){
            val identifierNode = addNewNodeToAST(GrammarSymbols.IDENTIFIER, currentLexem)

            getNextLexem()
            if(currentLexem.type == LexemType.LINEBREAK && identifierNode != null){
                parent = constructTree(GrammarSymbols.VARIABLES_LIST, arrayListOf(identifierNode))
                getNextLexem()
            }
            else if(currentLexem.type == LexemType.COMMA){
                val commaNode = addNewNodeToAST(GrammarSymbols.COMMA, currentLexem)

                getNextLexem()
                val variablesListNode = variablesList()

                if(identifierNode != null && commaNode!=null && variablesListNode!=null){
                    parent = constructTree(GrammarSymbols.VARIABLES_LIST, arrayListOf(identifierNode, commaNode, variablesListNode))
                }
            }
        }

        if(parent == null)
            printErrMsg("variablesList")
        return parent
    }

    //<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
    private fun operatorsList(){
        operator()
        if(lookAheadType() == LexemType.LINEBREAK){
            getNextLexem()
            getNextLexem()
        }
        else {
            getNextLexem()
            operatorsList()
        }
    }

    // <Оператор>::=<Присваивание> |<Сложный оператор>
    private fun operator(){
        assignment()
        complexOperator()
    }

    // <Присваивание> ::= <Идент> := <Выражение>
    private fun assignment(){
        if(currentLexem.type == LexemType.IDENTIFIER){
            addNewNodeToAST(GrammarSymbols.IDENTIFIER, currentLexem)
            getNextLexem()
            if(currentLexem.type == LexemType.DECLARE){
                addNewNodeToAST(GrammarSymbols.ASSIGNMENT_SIGN, currentLexem)
                getNextLexem()
                expression()
            }
        }
    }

    // <Выражение> ::= <Ун.оп.> <Подвыражение> | <Подвыражение>
    private fun expression(){
        if(currentLexem.type == LexemType.UNI_MATH_OPERATOR){
            addNewNodeToAST(GrammarSymbols.UNARY_OPERATOR, currentLexem)
            getNextLexem()
            subExpression()
        }
        else{
            subExpression()
        }
    }

    // <Подвыражение> :: = ( <Выражение> ) | <Операнд> |
    //<Подвыражение > <Бин.оп.> <Подвыражение>
    private fun subExpression(){

    }

    // <Сложный оператор> ::= IF < Выражение> THEN Оператор |
    // IF <Выражение> THEN <Оператор> ELSE <Оператор>|
    //<Составной оператор>
    private fun complexOperator(){

    }

    private fun getNextLexem(){
        currentLexem = lexemList[currentLexemIndex+1]
        currentLexemIndex++
    }

    private fun addNewNodeToAST(gs: GrammarSymbols, lexem: Lexem) : ASTNode? = ASTNode(gs, lexem)

    private fun constructTree(parentType: GrammarSymbols, children: ArrayList<ASTNode>): ASTNode? {
        val parent = ASTNode(parentType, null)
        children.forEach { child ->
            parent.addChild(child)
            child.setParent(parent)
        }
        return parent
    }
}