class ErrorLog{
    companion object {
        private var lineNum = 1
        private val errorTray = arrayListOf<String>()

        fun logError(msg:String){
            errorTray.add("Error in line $lineNum: $msg")
        }

        fun showErrorList(){
            errorTray.forEach {
                println(it)
            }
        }

        fun nextLine() = lineNum++
    }
}