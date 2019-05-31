
fun commonFunction() {
  println("Hello common code!")
}


interface Runnable {
  fun run()
}


fun <T> genericFunction(t: T): List<String>
        where T : Number,
              T : Runnable {
  t.run()
  return listOf("123" + t.toDouble())
}


