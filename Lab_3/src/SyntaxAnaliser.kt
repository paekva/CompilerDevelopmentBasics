import java.util.*
import kotlin.collections.ArrayList

typealias GrammarRule = ArrayList<GrammarSymbols>
typealias TableRow = Array<GrammarRule>
class SyntaxAnalyzer(private val lexemList: ArrayList<Lexem>) {

    private var currentLexem: Lexem = lexemList[0]
    private var currentLexemIndex: Int = 0
    private var stack: Stack<GrammarSymbols> = Stack()
    private fun lookAheadType(): LexemType = if(currentLexemIndex < lexemList.size) lexemList[currentLexemIndex+1].type else LexemType.LINEBREAK

    init{
        stack.push(GrammarSymbols.S)
    }

    // <Программа> ::= <Объявление переменных> <Описание вычислений>
    fun beginAnalise(){
        declareVariables()
        declareCalculations()
    }

    /*
<Ун.оп.> ::= "-"
<Бин.оп.> ::= "-" | "+" | "*" | "/" | ">>" | "<<" | ">" | "<" | "="
<Операнд> ::= <Идент> | <Const>
<Составной оператор>::= Begin < Список операторов > End*/

    // <Объявление переменных> ::= Var <Список переменных>
    private fun declareVariables(){
        if(currentLexem.type == LexemType.VAR){
            addNewNodeToAST(currentLexem)
            getNextLexem()
            variablesList()
        }
    }

    // <Описание вычислений> ::= Begin < Список операторов > End
    private fun declareCalculations(){
        if(currentLexem.type == LexemType.BEGIN){
            addNewNodeToAST(currentLexem)
            operatorsList()
            if(currentLexem.type == LexemType.END){
                addNewNodeToAST(currentLexem)
            }
        }
    }

    // <Список переменных> ::= <Идент> | <Идент> , <Список переменных>
    private fun variablesList(){
        if(currentLexem.type == LexemType.IDENTIFIER){
            addNewNodeToAST(currentLexem)

            if(lookAheadType() == LexemType.LINEBREAK){
                getNextLexem()
                getNextLexem()
            }
            else {
                if(lookAheadType() == LexemType.COMMA){
                    addNewNodeToAST(currentLexem)

                    getNextLexem()
                    variablesList()
                }
            }
        }
    }

    //<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
    private fun operatorsList(){
        operator()
        if(lookAheadType() == LexemType.LINEBREAK){
            getNextLexem()
            getNextLexem()
        }
        else {
            getNextLexem()
            operatorsList()
        }
    }

    // <Оператор>::=<Присваивание> |<Сложный оператор>
    private fun operator(){
        assignment()
        complexOperator()
    }

    // <Присваивание> ::= <Идент> := <Выражение>
    private fun assignment(){
        if(currentLexem.type == LexemType.IDENTIFIER){
            addNewNodeToAST(currentLexem)
            getNextLexem()
            if(currentLexem.type == LexemType.DECLARE){
                addNewNodeToAST(currentLexem)
                getNextLexem()
                expression()
            }
        }
    }

    // <Выражение> ::= <Ун.оп.> <Подвыражение> | <Подвыражение>
    private fun expression(){
        if(currentLexem.type == LexemType.UNI_MATH_OPERATOR){
            addNewNodeToAST(currentLexem)
            getNextLexem()
            subExpression()
        }
        else{
            subExpression()
        }
    }

    // <Подвыражение> :: = ( <Выражение> ) | <Операнд> |
    //<Подвыражение > <Бин.оп.> <Подвыражение>
    private fun subExpression(){

    }

    // <Сложный оператор> ::= IF < Выражение> THEN Оператор |
    // IF <Выражение> THEN <Оператор> ELSE <Оператор>|
    //<Составной оператор>
    private fun complexOperator(){

    }

    private fun getNextLexem(){
        currentLexem = lexemList[currentLexemIndex+1]
        currentLexemIndex++
    }

    private fun addNewNodeToAST(lexem: Lexem){
        println("${lexem.sign}")
    }
}