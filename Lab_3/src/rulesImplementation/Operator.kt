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
        if(assignmentNode!=null)
            children.add(assignmentNode)
        else {
            val complexOperatorNode = ComplexOperator(getCurrentLexeme, moveToTheNextLexeme).analyze()
            children.add(complexOperatorNode)
        }

        lineBreak()
        val parent = constructTree(GrammarSymbols.OPERATOR, children)
        if (parent == null)
            printErrMsg("operator")
        return parent
    }

    private fun lineBreak(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.LINEBREAK)
            return true
        return false
    }
}