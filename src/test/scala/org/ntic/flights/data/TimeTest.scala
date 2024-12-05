package org.ntic.flights.data

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class TimeTest extends AnyFlatSpec with Matchers {
  "A Time" should "be correctly initialized from string" in {
    val timeStr1 = "650"
    val timeStr2 = "1440"
    val timeStr3 = "3274"
    val expected1 = Time(6, 50)
    val expected2 = Time(14, 40)
    val expected3 = Time(8, 14)

    val result1 = Time.fromString(timeStr1)
    val result2 = Time.fromString(timeStr2)
    val result3 = Time.fromString(timeStr3)
    result1 shouldEqual expected1
    result2 shouldEqual expected2
    result3 shouldEqual expected3
  }

  "A time" should "be correctly initialized from negative minutes" in {
    val result = Time.fromMinutes(-30)
    val expected = Time(0, 0)
    result shouldEqual expected
  }

  "A time" should "be correctly initialized minutes greater than the maximum minutes in a day" in {
    val result = Time.fromMinutes(1540)
    val expected = Time(1, 40)
    result shouldEqual expected
  }

  "A Time" should "compare two Time objects correctly" in {
    val t1 = Time(10, 30)
    val t2 = Time(12, 15)

    (t1 < t2) shouldEqual true
    (t1 > t2) shouldEqual false
    (t1 == t2) shouldEqual false
    t1.compare(t2) should be < 0
  }

  "A Time" should "not allow invalid hour values" in {
    intercept[IllegalArgumentException] {
      Time(24, 0)
    }.getMessage shouldEqual "requirement failed: hours must be within 0 and 23"
  }

  "A Time" should "not allow invalid minute values" in {
    intercept[IllegalArgumentException] {
      Time(10, 60)
    }.getMessage shouldEqual "requirement failed: minutes must be within 0 and 59"
  }
}



