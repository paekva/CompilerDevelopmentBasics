import rulesImplementation.KeyWords
import rulesImplementation.OperatorsList
import rulesImplementation.VariablesList
import kotlin.collections.ArrayList

class SyntaxAnalyzer{
    companion object {
        private var lexemeList: ArrayList<Lexem> = arrayListOf()
        private var currentLexeme: Lexem = Lexem(LexemType.UNRECOGNISED, "")
        private var currentLexemeIndex: Int = 0

        fun setLexemeList(lexemes: ArrayList<Lexem>) {
            if (lexemeList.isEmpty()) {
                lexemeList = lexemes
                currentLexeme = lexemeList[0]
            }
        }

        fun beginAnalise(): ASTNode? = program()
        fun getCurrentLexeme():Lexem = currentLexeme
        fun moveToTheNextLexeme(): Lexem {
            getNextLexeme()
            return currentLexeme
        }
        fun skipCurrentLine(): Lexem {
            while (currentLexeme.type != LexemType.LINEBREAK) getNextLexeme()
            getNextLexeme()

            return currentLexeme
        }

        // <Программа> ::= <Объявление переменных> <Описание вычислений>
        private fun program(): ASTNode? {
            val declareVariablesNode = declareVariables()
            val declareCalculationsNode = declareCalculations()

            if (currentLexemeIndex != lexemeList.size-1)
                ErrorLog.logError("Some extra lines after the end of the calculations block ")

            return constructTree(GrammarSymbols.PROGRAM, arrayListOf(declareVariablesNode, declareCalculationsNode))
        }

        // <Объявление переменных> ::= Var <Список переменных>
        private fun declareVariables(): ASTNode? {
            val variableNode = KeyWords.variable()
            variableNode ?: run {
                ErrorLog.logError("No key word 'Var' at the start of variables declare")
                return null
            }
            val variablesListNode = variablesList()
            variablesListNode ?: return null

            lineBreak()
            return constructTree(GrammarSymbols.DECLARE_VARIABLES, arrayListOf(variableNode, variablesListNode))
        }

        // <Описание вычислений> ::= Begin < Список операторов > End
        private fun declareCalculations(): ASTNode? {
            val beginNode = KeyWords.begin()
            beginNode ?: run {
                ErrorLog.logError("No begin at the start of calculation")
                return null
            }

            val operatorsListNode = operatorsList()

            val endNode = KeyWords.end()
            endNode ?: run {
                ErrorLog.logError("No end at the end of calculation")
                return null
            }

            return constructTree(
                GrammarSymbols.DECLARE_CALCULATIONS,
                arrayListOf(beginNode, operatorsListNode, endNode)
            )
        }

        // <Список переменных> ::= <Идент> | <Идент> , <Список переменных>
        private fun variablesList(): ASTNode? {
            return VariablesList().analyze()
        }

        //<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
        private fun operatorsList(): ASTNode? {
            return OperatorsList().analyze()
        }

        private fun getNextLexeme() {
            if (currentLexemeIndex + 1 < lexemeList.size) {
                currentLexeme = lexemeList[currentLexemeIndex + 1]
                currentLexemeIndex++
            }
        }

        private fun lineBreak(): Boolean {
            if (currentLexeme.type == LexemType.LINEBREAK) {
                ErrorLog.nextLine()
                getNextLexeme()
                return true
            }
            return false
        }

    }
}