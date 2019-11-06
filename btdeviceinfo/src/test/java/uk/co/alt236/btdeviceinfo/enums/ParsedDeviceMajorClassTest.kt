package uk.co.alt236.btdeviceinfo.enums

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class ParsedDeviceMajorClassTest {
    @Test
    fun `ensure that there are no duplicate android ids`() {
        val set = HashSet<Int>()

        for (value in ParsedDeviceMajorClass.values()) {
            val result = set.add(value.androidIdentifier)
            if (!result) {
                fail("The identifier of $value has already been used!")
            }
        }
    }

    @Test
    fun `ensure that android ids parse to the enums and back`() {
        for (value in ParsedDeviceMajorClass.values()) {
            assertEquals(
                value,
                ParsedDeviceMajorClass.fromAndroidIdentifier(value.androidIdentifier)
            )
        }
    }

    @Test
    fun `ensure that unknown ids parse as unknown`() {
        assertEquals(
            ParsedDeviceMajorClass.UNKNOWN,
            ParsedDeviceMajorClass.fromAndroidIdentifier(Int.MAX_VALUE)
        )
        assertEquals(
            ParsedDeviceMajorClass.UNKNOWN,
            ParsedDeviceMajorClass.fromAndroidIdentifier(Int.MIN_VALUE)
        )
    }
}