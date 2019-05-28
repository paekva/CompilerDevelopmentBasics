package rulesImplementation

import ASTNode
import constructTree

class Operator{

    // <Оператор>::= <Присваивание>|<Сложный оператор>
    fun analyze(): ASTNode? {
        val children: ArrayList<ASTNode?> = arrayListOf()
        var complexOperatorNode: ASTNode?

        val assignmentNode = Assignment().analyze()

        if(assignmentNode!=null)
            children.add(assignmentNode)
        else {
            complexOperatorNode = ComplexOperator().analyze()
            if(complexOperatorNode!=null)
                children.add(complexOperatorNode)
        }

        return constructTree(GrammarSymbols.OPERATOR, children)
    }
}