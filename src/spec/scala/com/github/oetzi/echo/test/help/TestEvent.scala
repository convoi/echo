package com.github.oetzi.echo.test.help

import com.github.oetzi.echo.core.EventSource
import com.github.oetzi.echo.Echo._

class TestEvent[T] extends EventSource[T] {
  def pubOccur(value : T) {
    occur(value)
  }
}
