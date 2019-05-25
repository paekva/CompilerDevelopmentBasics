import rulesImplementation.OperatorsList
import kotlin.collections.ArrayList

class SyntaxAnalyzer(private val lexemList: ArrayList<Lexem>) {

    private var currentLexeme: Lexem = lexemList[0]
    private var currentLexemeIndex: Int = 0
    private fun getCurrentLexeme() = currentLexeme
    private fun moveToTheNextLexeme(): Lexem {
        getNextLexeme()
        return currentLexeme
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

        if(currentLexeme.type == LexemType.VAR){
            val varNode = addNewNodeToAST(GrammarSymbols.VAR, currentLexeme)
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
        if(currentLexeme.type == LexemType.BEGIN){
            val beginNode = addNewNodeToAST(GrammarSymbols.BEGIN, currentLexeme)
            children.add(beginNode)
            getNextLexeme()
            skipLineBreak()

            val operatorsListNode = operatorsList()
            children.add(operatorsListNode)
            getNextLexeme()

            if(currentLexeme.type == LexemType.END){
                val endNode = addNewNodeToAST(GrammarSymbols.END, currentLexeme)
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
        if(currentLexeme.type == LexemType.IDENTIFIER){
            val identifierNode = addNewNodeToAST(GrammarSymbols.IDENTIFIER, currentLexeme)
            children.add(identifierNode)
            getNextLexeme()

            skipLineBreak()

            if(currentLexeme.type == LexemType.COMMA){
                val commaNode = addNewNodeToAST(GrammarSymbols.COMMA, currentLexeme)
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
        return OperatorsList(::getCurrentLexeme, ::moveToTheNextLexeme).analyze()
    }
    
    private fun getNextLexeme(){
        currentLexeme = lexemList[currentLexemeIndex+1]
        currentLexemeIndex++
    }

    private fun addNewNodeToAST(gs: GrammarSymbols, lexem: Lexem) : ASTNode? = ASTNode(gs, lexem)

    private fun skipLineBreak(): Boolean{
        if(currentLexeme.type == LexemType.LINEBREAK){
            getNextLexeme()
            return true
        }
        return false
    }
}