@file:Suppress("PackageDirectoryMismatch")

package _tasks.bars



class Bag(
        val weight: Int
)

class UserInfo(
        val name: String,
        val hello: String?,
        val bags : List<Bag>
)

fun loadUsers() : List<UserInfo> = TODO()

/// Example to compute total weight of all bags
fun totalWeight() = loadUsers()
        .flatMap { it.bags }
        .sumBy { it.weight }


fun howManyBagsToWeAllHave() : Int {
  val allUsers = loadUsers()

  return TODO("Compute the number of bags in $allUsers")
}


