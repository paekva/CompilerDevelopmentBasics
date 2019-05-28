import rulesImplementation.KeyWords
import rulesImplementation.OperatorsList
import rulesImplementation.VariablesList
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
        val variableNode = KeyWords(::getCurrentLexeme, ::moveToTheNextLexeme).variable()
        val variablesListNode = variablesList()

        lineBreak()

        val parent = constructTree(GrammarSymbols.DECLARE_VARIABLES, arrayListOf(variableNode, variablesListNode))
        if(parent == null)
            printErrMsg("declareVariables")
        return parent
    }

    // <Описание вычислений> ::= Begin < Список операторов > End
    private fun declareCalculations() : ASTNode? {
        val keyWordsService = KeyWords(::getCurrentLexeme, ::moveToTheNextLexeme)

        val beginNode = keyWordsService.begin()
        lineBreak()

        val operatorsListNode = operatorsList()

        val endNode = keyWordsService.end()
        lineBreak()

        val parent = constructTree(GrammarSymbols.DECLARE_VARIABLES, arrayListOf(beginNode, operatorsListNode, endNode))
        if(parent == null)
            printErrMsg("declareCalculations")
        return parent
    }

    // <Список переменных> ::= <Идент> | <Идент> , <Список переменных>
    private fun variablesList(): ASTNode?{
        return VariablesList(::getCurrentLexeme, ::moveToTheNextLexeme).analyze()
    }

    //<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
    private fun operatorsList() : ASTNode? {
        return OperatorsList(::getCurrentLexeme, ::moveToTheNextLexeme).analyze()
    }
    
    private fun getNextLexeme(){
        currentLexeme = lexemList[currentLexemeIndex+1]
        currentLexemeIndex++
    }

    private fun lineBreak(): Boolean{
        if(currentLexeme.type == LexemType.LINEBREAK){
            getNextLexeme()
            return true
        }
        return false
    }
}