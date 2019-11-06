package uk.co.alt236.btdeviceinfo.enums

import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Test

class ParsedDeviceClassTest {

    @Test
    fun `ensure that there are no duplicate android ids`() {
        val set = HashSet<Int>()

        for (value in ParsedDeviceClass.values()) {
            val result = set.add(value.androidIdentifier)
            if (!result) {
                fail("The identifier of $value has already been used!")
            }
        }
    }

    @Test
    fun `ensure that android ids parse to the enums and back`() {
        for (value in ParsedDeviceClass.values()) {
            Assert.assertEquals(
                value,
                ParsedDeviceClass.fromAndroidIdentifier(value.androidIdentifier)
            )
        }
    }

    @Test
    fun `ensure that unknown ids parse as unknown`() {
        Assert.assertEquals(
            ParsedDeviceClass.UNKNOWN,
            ParsedDeviceClass.fromAndroidIdentifier(Int.MAX_VALUE)
        )
        Assert.assertEquals(
            ParsedDeviceClass.UNKNOWN,
            ParsedDeviceClass.fromAndroidIdentifier(Int.MIN_VALUE)
        )
    }
}