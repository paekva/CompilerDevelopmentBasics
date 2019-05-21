class Lexem(val type: LexemType, val value: String)

enum class LexemType(val code: Int){
    RELATION_OPERATOR(0),
    BIN_MATH_OPERATOR(1),
    UNI_MATH_OPERATOR(2),
    WORD(3),
    IDENTIFIER(4),
    CONST(5),
    UNRECOGNISED(6)
}

enum class Words(val lexem: String) {
    Is(":="),
    LBRACE("("),
    RBRACE(")"),
    Begin("Begin"),
    End("End"),
    Var("Var"),
    If("IF"),
    Then("THEN"),
    Else("ELSE"),
}

enum class Relations(val lexem: String) {
    MMore(">>"),
    MLess("<<"),
    More(">"),
    Less("<"),
    Equal("=")
}

enum class BinMathOperators(val lexem: String){
    Minus("-"),
    Plus("+"),
    Mul("*"),
    Div("/"),
}

enum class UniMathOperators(val lexem: String){
    Minus("-"),
}