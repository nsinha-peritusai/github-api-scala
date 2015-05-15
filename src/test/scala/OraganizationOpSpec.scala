import org.scalatest.FunSpec
import scala.concurrent.Await

import codecheck.github.models.OrganizationInput

class OrganizationOpSpec extends FunSpec with Constants {

  val gName = generateRandomString()
  val gCompany = generateRandomString()
  val gDescription = generateRandomString()
  val gLocation = generateRandomString()

  describe("listOwnOrganizations(user)") {
    it("should return at least one organization.") {
      val result = Await.result(api.listOwnOrganizations, TIMEOUT)
      assert(result.length >= 1)
    }

    it("should return multiple organizations if user belongs in more than one.") {
      val result = Await.result(api.listOwnOrganizations, TIMEOUT)
      assert(result.length > 1)
    }
  }

  describe("listUserOrganizations") {
    it("should return at least one organization.") {
      val result = Await.result(api.listUserOrganizations(user), TIMEOUT)
      assert(result.length >= 1)
    }

    it("should return multiple organizations if user belongs in more than one.") {
      val result = Await.result(api.listUserOrganizations(user), TIMEOUT)
      assert(result.length > 1)
    }
  }
  /*
  describe("updateOrganization") {
    import OrganizationInput._
    it("should return true if values updated correctly") {
      val input = new OrganizationInput(gName, gCompany, gDescription, gLocation)
      println(input.name)
      Await.result(api.updateOrganization(organization, input), TIMEOUT).map { org =>
        assert(org.name == gName)
        println(org.company)
        assert(org.description == gDescription)
        assert(org.location == gLocation)
      }
    }
  } */

  describe("getOrganization") {
    it("should return correct values.") {
      Await.result(api.getOrganization(organization), TIMEOUT).map { org =>
        assert(org.login == "celestialbeings")
        assert(org.id > 0)
        assert(org.url == "https://api.github.com/orgs/celestialbeings")
        assert(org.avatar_url.length > 0)
        //assert(org.name == gName)
        //assert(org.company == gCompany)
        //assert(org.description == gDescription)
        //assert(org.location == gLocation)
        //assert(org.email == "")
        //assert(org.billing_email == "")
        //assert(org.public_repos == 0)
        //assert(org.public_gists == 0)
      }
    }

    it("should return None if invalid organization.") {
      assert(Await.result(api.getOrganization("code-check-x"), TIMEOUT).isEmpty)
    }
  }
  
  
}
