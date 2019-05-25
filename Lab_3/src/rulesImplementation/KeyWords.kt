package rulesImplementation

import currentLexeme
import ASTNode

class KeyWords(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme){

    // Begin
    fun begin(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.BEGIN) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.BEGIN, lexeme)
        }
        return null
    }

    // Var
    fun variable(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.VAR) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.VAR, lexeme)
        }
        return null
    }

    // End
    fun end(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.END) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.END, lexeme)
        }
        return null
    }
}