class ASTNode(private val type: LexemType, private val textSign: String) {

    private var parent: ASTNode? = null
    private val children: ArrayList<ASTNode> = arrayListOf<ASTNode>()

    fun addChild(child: ASTNode){
        val prevParent = child.getParent()

        if(prevParent != null)
            prevParent.getChildren().remove(child)

        children.add(child)
        child.setParent(this)
    }

    fun removeChild(child: ASTNode){
        if(child.getParent() == null)
            return

        children.remove(child)
        child.setParent(null)
    }

    fun getParent(): ASTNode? {
        return parent
    }

    fun setParent(newParent: ASTNode?) {
        parent = newParent
    }

    fun getChildren(): ArrayList<ASTNode> {
        return children
    }
}

enum class LexemType(val code: Int){
    RELATION_OPERATOR(0),
    BIN_MATH_OPERATOR(1),
    UNI_MATH_OPERATOR(2),
    WORD(3),
    IDENTIFIER(4),
    CONST(5),
    UNRECOGNISED(6);
}

class Lexem(val type: LexemType, val sign: String)