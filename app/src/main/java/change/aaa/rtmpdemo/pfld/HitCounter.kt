package change.aaa.rtmpdemo.pfld

class HitCounter() {

    var counter = 0

    var rate = 0

    init {
        Thread{
            while (true) {
                rate = counter
                counter = 0;
                Thread.sleep(1000)
            }
        }.start()
    }

    fun count() {
        counter++
    }
}