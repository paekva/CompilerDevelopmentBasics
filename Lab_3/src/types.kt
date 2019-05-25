
typealias currentLexeme = () -> Lexem
fun printErrMsg(ruleName: String) = println("problem constructing tree: $ruleName")

fun constructTree(parentType: GrammarSymbols, children: ArrayList<ASTNode?>): ASTNode? {
    if(children.isEmpty())
        return null

    val parent = ASTNode(parentType, null)

    children.forEach { child ->
        if(child == null ) return null
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

enum class GrammarSymbols(val sign: String, val type: GrammarSymbolsTypes, val index: Int){
    PROGRAM("<Программа>", GrammarSymbolsTypes.NONTERMINAL, 0),
    DECLARE_VARIABLES("<Объявление переменных>", GrammarSymbolsTypes.NONTERMINAL, 2),
    DECLARE_CALCULATIONS("<Описание вычислений>", GrammarSymbolsTypes.NONTERMINAL, 1),
    OPERATORS_LIST("<Список операторов>", GrammarSymbolsTypes.NONTERMINAL, 4),
    VARIABLES_LIST("<Список переменных>", GrammarSymbolsTypes.NONTERMINAL, 3),
    IDENTIFIER("<Идент>", GrammarSymbolsTypes.NONTERMINAL, 14),
    OPERATOR("<Оператор>", GrammarSymbolsTypes.NONTERMINAL, 5),
    ASSIGNMENT("<Присваивание>", GrammarSymbolsTypes.NONTERMINAL, 6),
    COMPLEX_OPERATOR("<Сложный оператор>", GrammarSymbolsTypes.NONTERMINAL,12),
    EXPRESSION("<Выражение>", GrammarSymbolsTypes.NONTERMINAL, 7),
    SUB_EXPRESSION("<Подвыражение>", GrammarSymbolsTypes.NONTERMINAL, 8),
    UNARY_OPERATOR("<Ун.оп.>", GrammarSymbolsTypes.NONTERMINAL, 9),
    OPERAND("<Операнд>", GrammarSymbolsTypes.NONTERMINAL, 11),
    BINARY_OPERATOR("<Бин.оп.>", GrammarSymbolsTypes.NONTERMINAL, 10),
    CONST("<Const>", GrammarSymbolsTypes.NONTERMINAL, 15),
    COMPOUND_OPERATOR("<Составной оператор>", GrammarSymbolsTypes.NONTERMINAL, 13),
    BEGIN("Begin", GrammarSymbolsTypes.TERMINAL, 0),
    END("End", GrammarSymbolsTypes.TERMINAL, 2),
    VAR("Var", GrammarSymbolsTypes.TERMINAL, 3),
    COMMA(",", GrammarSymbolsTypes.TERMINAL,4),
    ASSIGNMENT_SIGN(":=", GrammarSymbolsTypes.TERMINAL,5),
    LEFT_BRACE("(", GrammarSymbolsTypes.TERMINAL,6),
    RIGHT_BRACE(")", GrammarSymbolsTypes.TERMINAL,7),
    MINUS("-", GrammarSymbolsTypes.TERMINAL,8),
    PLUS("+", GrammarSymbolsTypes.TERMINAL,9),
    MUL("*", GrammarSymbolsTypes.TERMINAL,10),
    DIV("/", GrammarSymbolsTypes.TERMINAL,11),
    LEFT_SHIFT_SIGN(">>", GrammarSymbolsTypes.TERMINAL,12),
    RIGHT_SHIFT_SIGN("<<", GrammarSymbolsTypes.TERMINAL,13),
    LESS_SIGN(">", GrammarSymbolsTypes.TERMINAL,14),
    MORE_SIGN("<", GrammarSymbolsTypes.TERMINAL,15),
    EQUAL_SIGN("=", GrammarSymbolsTypes.TERMINAL,16),
    IF("IF", GrammarSymbolsTypes.TERMINAL,17),
    THEN("THEN", GrammarSymbolsTypes.TERMINAL,18),
    ELSE("ELSE", GrammarSymbolsTypes.TERMINAL,19),
    LETTER("<Буква>", GrammarSymbolsTypes.TERMINAL,20),
    DIGIT("<Цифра>", GrammarSymbolsTypes.TERMINAL,21),
    TMP_NODE("", GrammarSymbolsTypes.NONTERMINAL,22),
}

enum class GrammarSymbolsTypes{
    TERMINAL,
    NONTERMINAL
}
class Lexem(val type: LexemType, val sign: String)