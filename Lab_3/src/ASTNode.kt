class ASTNode(val type: GrammarSymbols, val lexem: Lexem?) {

    private var parent: ASTNode? = null
    private val children: ArrayList<ASTNode> = arrayListOf()

    fun addChild(child: ASTNode){
        val prevParent = child.getParent()

        if(prevParent != null)
            prevParent.getChildren().remove(child)

        children.add(child)
        child.setParent(this)
    }

    private fun getParent(): ASTNode? {
        return parent
    }

    fun setParent(newParent: ASTNode?) {
        parent = newParent
    }

    fun getChildren(): ArrayList<ASTNode> {
        return children
    }
}