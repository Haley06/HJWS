package change.aaa.rtmpdemo.pfld

data class FatigueFeatureItemReq(
//    val token: String,
//    val uuid: String,
    val P_70: Float,
    val max_mouth: Int,
//    val timestamp: Long,
    val a: Float,
    val b: Float,
    val c: Float,
    val d: Float,
    val e: Float,
    val head_points: Array<IntArray>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FatigueFeatureItemReq

        if (P_70 != other.P_70) return false
        if (max_mouth != other.max_mouth) return false
        if (a != other.a) return false
        if (b != other.b) return false
        if (c != other.c) return false
        if (d != other.d) return false
        if (e != other.e) return false
        if (!head_points.contentDeepEquals(other.head_points)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = P_70.hashCode()
        result = 31 * result + max_mouth
        result = 31 * result + a.hashCode()
        result = 31 * result + b.hashCode()
        result = 31 * result + c.hashCode()
        result = 31 * result + d.hashCode()
        result = 31 * result + e.hashCode()
        result = 31 * result + head_points.contentDeepHashCode()
        return result
    }
}

data class FatigueFeatureItemRes(
//    val uuid: String,
    val pred: Float,
    var timestamp: Long,
    var emotion_res: Int,
    var head_res: Int
)

data class DataTest(
    val uuid: String,
    val pred: Float,
    var timestamp: Long,
//    var emotion_res: String,
//    var head_res: Int
)
