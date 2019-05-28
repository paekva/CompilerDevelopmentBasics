package rulesImplementation

import ASTNode
import constructTree
import currentLexeme

class Operator(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme){

    // <Оператор>::= <Присваивание>|<Сложный оператор>
    fun analyze(): ASTNode? {
        val children: ArrayList<ASTNode?> = arrayListOf()

        val assignmentNode = Assignment(getCurrentLexeme, moveToTheNextLexeme).analyze()
        if(assignmentNode!=null)
            children.add(assignmentNode)
        else {
            val complexOperatorNode = ComplexOperator(getCurrentLexeme, moveToTheNextLexeme).analyze()
            children.add(complexOperatorNode)
        }

        removeLineBreak()
        return constructTree(GrammarSymbols.OPERATOR, children)
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