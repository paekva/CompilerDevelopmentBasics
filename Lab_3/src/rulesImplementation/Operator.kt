package rulesImplementation

import ASTNode
import constructTree
import currentLexeme
import printErrMsg

class Operator(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme){

    // <Оператор>::= <Присваивание>|<Сложный оператор>
    fun analyze(): ASTNode? {
        val lexeme = getCurrentLexeme()

        val children: ArrayList<ASTNode?> = arrayListOf()

        val assignmentNode = Assignment(getCurrentLexeme, moveToTheNextLexeme).analyze()
        val lexem = getCurrentLexeme()
        if(assignmentNode!=null)
            children.add(assignmentNode)
        else {
            val complexOperatorNode = ComplexOperator(getCurrentLexeme, moveToTheNextLexeme).analyze()
            children.add(complexOperatorNode)
        }

        removeLineBreak()
        val parent = constructTree(GrammarSymbols.OPERATOR, children)
        if (parent == null)
            printErrMsg("operator")
        return parent
    }

    private fun removeLineBreak(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.LINEBREAK) {
            moveToTheNextLexeme()
            return true
        }
        return false
    }
}