fun main(args: Array<String>){
    val ui = UI()
    val input = ui.getInput()

    val recogniser = Recogniser(ui.arrayListOfRules, ui.alphabet, ui.relations)

    try {
        recogniser.recognise(input)
    }
    catch(e: Exception){
        println("Does not belong to grammar")
    }

}