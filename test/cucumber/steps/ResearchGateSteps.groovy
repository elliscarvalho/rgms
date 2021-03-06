import pages.BibtexGenerateFilePage
import pages.LoginPage
import pages.PublicationsPage
import pages.ResearchGatePage
import rgms.authentication.User
import rgms.publication.Periodico
import steps.ArticleTestDataAndOperations

import static cucumber.api.groovy.EN.*
// #if($researchGate)
Given(~'^my Research Gate credentials were not given to the system previously$') { ->
    assert User.findByUsername("admin").author.researchGate_username == null
}

Then(~'^I can fill my Research Gate credentials$') { ->
    at ResearchGatePage
    page.fillCredentials()
}

Given(~'^I am at the export to research gate page$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    page.select("Export Bibtex File")
    at BibtexGenerateFilePage
    page.select("Generate All BibTex")
    page.select("Export to Research Gate")
    at ResearchGatePage
}

Given(~'^I had filled the Research Gate credentials with valid credentials$') { ->
    at ResearchGatePage
    page.fillCredentials()
    page.select("Log in")
}

When(~'^I click on the button Export$') { ->
    at ResearchGatePage
    page.clickOnExport()
}

Then(~'^I can see the articles "([^"]*)" and "([^"]*)" on the Research Gate website$') { String article1, article2 ->
    at ResearchGatePage
    assert page.consultOnResearchGate(article1) != null
    assert page.consultOnResearchGate(article2) != null
}

// #end