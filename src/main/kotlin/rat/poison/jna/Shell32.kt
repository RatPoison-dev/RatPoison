/////////////////////////////////
////This was provided by Mr. Noad
/////////////////////////////////

package rat.poison.jna

import com.sun.jna.LastErrorException
import com.sun.jna.Native
import com.sun.jna.ptr.LongByReference

const val QUNS_NOT_PRESENT = 0L
const val QUNS_BUSY = 1L
const val QUNS_RUNNING_D3D_FULL_SCREEN = 2L
const val QUNS_PRESENTATION_MODE = 3L
const val QUNS_ACCEPTS_NOTIFICATIONS = 4L
const val QUNS_QUIET_TIME = 5L
const val QUNS_APP = 6L

object Shell32 {
    init {
        Native.register("shell32")
    }
    @Throws(LastErrorException::class)
    external fun SHQueryUserNotificationState(state: LongByReference): Boolean
}