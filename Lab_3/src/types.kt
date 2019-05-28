fun printErrMsg(ruleName: String) = println("problem constructing tree: $ruleName")

fun constructTree(parentType: GrammarSymbols, children: ArrayList<ASTNode?>): ASTNode? {
    if(children.isEmpty())
        return null

    val parent = ASTNode(parentType, null)

    children.forEach { child ->
        child ?: return parent

        parent.addChild(child)
        child.setParent(parent)
    }
    return parent
}

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

enum class GrammarSymbols(val sign: String, val index: Int){
    PROGRAM("<Программа>", 0),
    DECLARE_VARIABLES("<Объявление переменных>", 2),
    DECLARE_CALCULATIONS("<Описание вычислений>", 1),
    OPERATORS_LIST("<Список операторов>", 4),
    VARIABLES_LIST("<Список переменных>", 3),
    IDENTIFIER("<Идент>", 14),
    OPERATOR("<Оператор>", 5),
    ASSIGNMENT("<Присваивание>", 6),
    COMPLEX_OPERATOR("<Сложный оператор>",12),
    EXPRESSION("<Выражение>",  7),
    SUB_EXPRESSION("<Подвыражение>", 8),
    UNARY_OPERATOR("<Ун.оп.>", 9),
    OPERAND("<Операнд>", 11),
    BINARY_OPERATOR("<Бин.оп.>", 10),
    CONST("<Const>", 15),
    COMPOUND_OPERATOR("<Составной оператор>", 13),
    BEGIN("Begin", 0),
    END("End", 2),
    VAR("Var", 3),
    COMMA(",", 4),
    ASSIGNMENT_SIGN(":=",5),
    LEFT_BRACE("(", 6),
    RIGHT_BRACE(")", 7),
    MINUS("-", 8),
    PLUS("+", 9),
    MUL("*", 10),
    DIV("/", 11),
    LEFT_SHIFT_SIGN(">>", 12),
    RIGHT_SHIFT_SIGN("<<", 13),
    LESS_SIGN(">", 14),
    MORE_SIGN("<", 15),
    EQUAL_SIGN("=", 16),
    IF("IF", 17),
    THEN("THEN", 18),
    ELSE("ELSE", 19),
    LETTER("<Буква>", 20),
    DIGIT("<Цифра>", 21)
}

class Lexem(val type: LexemType, val sign: String)