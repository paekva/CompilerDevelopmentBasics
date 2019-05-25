class Lexem(val type: LexemType, val value: String)

enum class LexemType(val code: Int){
    RELATION_OPERATOR(0),
    BIN_MATH_OPERATOR(1),
    UNI_MATH_OPERATOR(2),
    DECLARE(3),
    IDENTIFIER(4),
    CONST(5),
    BEGIN(6),
    END(7),
    VAR(8),
    IF(9),
    THEN(10),
    ELSE(11),
    LBRACE(12),
    RBRACE(13),
    COMMA(14),
    LINEBREAK(15),
    UNRECOGNISED(16)
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