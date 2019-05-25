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