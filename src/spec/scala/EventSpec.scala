import org.specs._
import com.github.oetzi.echo.core.Event
import com.github.oetzi.echo.core.Occurence
import com.github.oetzi.echo.Echo._

object EventSpec extends Specification {
	"Event" should {
		"be created without paramaters" in {
			val event = new Event[Int]
			event.isInstanceOf[Event[Int]] mustBe true
		}
	}
	
	"Event.occs" should {
		"return an empty list for new Events" in {
			val event = new Event[Int]
			
			event.occs.isEmpty mustBe true
		}
		
		"returns a List with the correct length" in {
			val event = new Event[Int]
			event.occur(new Occurence(now, 5))
			event.occur(new Occurence(now, 5))
			
			event.occs.length mustBe 2
		}
		
		"returns a list with the correct values" in {
			val event = new Event[Int]
			val occurence = new Occurence(now, 5)
			event.occur(occurence)
			
			event.occs()(0) mustBe occurence
		}
	}
	
	"Event.occur" should {
		"increase the length of occs" in {
			val event = new Event[Int]
			val length = event.occs.length
			event.occur(new Occurence(now, 5))
			
			event.occs.length mustBe length + 1
		}
		
		"maintains order on added occurences" in {
			val event = new Event[Int]
			event.occur(new Occurence(15, 5))
			event.occur(new Occurence(10, 5))
			
			event.occs.last.time mustBe 15L
		}
	}
	
	"Event.merge" should {
		"return an Event that contains all the occurences of each" in {
			val eventOne = new Event[Int]
			val eventTwo = new Event[Int]
			val occurOne = new Occurence(now, 5)
			val occurTwo = new Occurence(now, 5)
			eventOne.occur(occurOne)
			eventTwo.occur(occurTwo)
			var matcher = List(occurOne, occurTwo)
			
			eventOne.merge(eventTwo).occs.foreach { occurence => 
				if (matcher.contains(occurence)) {
					matcher = matcher -- List(occurence)
				}
			}
			
			matcher.isEmpty mustBe true
		}
		
		"return an Event with ordered occurences of each" in {
			val eventOne = new Event[Int]
			val eventTwo = new Event[Int]
			val occurOne = new Occurence(10, 5)
			val occurTwo = new Occurence(15, 5)
			eventOne.occur(occurOne)
			eventTwo.occur(occurTwo)
			var last : Occurence[Int] = new Occurence(0, 0)
			
			eventTwo.merge(eventOne).occs.foreach { occurence => 
				last.time must be_<=(occurence.time)
				last = occurence	
			}
		}
		
		"return an Event that updates when the original updates" in {
			val eventOne = new Event[Int]
			val eventTwo = new Event[Int]
			
			val combEvent = eventOne.merge(eventTwo)
			eventOne.occur(new Occurence(now, 5))
			
			combEvent.occs.length mustBe 1
		}
		
		"return an Event that updates when the paramater updates" in {
			val eventOne = new Event[Int]
			val eventTwo = new Event[Int]
			
			val combEvent = eventOne.merge(eventTwo)
			eventTwo.occur(new Occurence(now, 5))
			
			combEvent.occs.length mustBe 1
		}
	}
}