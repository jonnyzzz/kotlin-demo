@file:Suppress("PackageDirectoryMismatch", "unused", "UNUSED_VARIABLE")

package org.jonnyzzz.demo.idioms

inline class Id(val name: String)
class Item

fun returns() {
  run {



    fun findItem(id: Id): Item? = TODO("$id")

    fun Item.loadInfo(): String? = TODO()


    fun loadMoreInfo(id: Id): String? {
      val item = findItem(id)
      if (item == null) return null

      val info = item.loadInfo()
      if (info == null) {
        throw Exception("No Info for $id")
      }
      return info
    }

  }

  run {

    fun findItem(id: Id): Item? = TODO("$id")
    fun Item.loadInfo(): String? = TODO()

    fun loadMoreInfo(id: Id): String? {
      val item = findItem(id) ?: return null
      return item.loadInfo() ?: throw Exception("…")
    }


  }

  run {

    fun findItem(id: Id): Item? = TODO("$id")
    fun Item.loadInfo(): String? = TODO()

    fun loadMoreInfo(id: Id) =
            findItem(id)?.let {
              it.loadInfo() ?: throw Exception("…")
            }

  }

  run {

    fun findItem(id: Id): Item? = TODO("$id")
    fun Item.loadInfo(): String? = TODO()

    fun loadMoreInfo(id: Id) =
            findItem(id)?.loadInfo()

  }
}


@Suppress("UNREACHABLE_CODE", "UNUSED_VARIABLE")
fun notghinh(): String {
  fun smth(): String? = "nul"
  fun compute(): List<Item>? = listOf()
  fun findItem(): Item? = null

  val p = return "@jonnyzzz"
  val q = throw Exception()
  val t = TODO("I'm lazy!")

  val x: Int? = p
  val y: String = smth() ?: TODO()
  val z: List<Pair<Int, Long>> = t


  val b = smth()?.trim()
          ?: throw Exception("sorry")

  val c = compute()?.firstOrNull()
          ?: TODO("null?!")

}

