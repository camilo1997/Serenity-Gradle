package com.reto.tecnico.stepdefinitions;

import com.reto.tecnico.models.User;
import com.reto.tecnico.questions.GetLastResponse;
import com.reto.tecnico.questions.GetUserId;
import com.reto.tecnico.tasks.builder.Create;
import com.reto.tecnico.tasks.builder.Update;
import com.reto.tecnico.utils.Generate;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;

import static com.reto.tecnico.utils.Constant.*;
import static com.reto.tecnico.utils.Generate.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

public class UpdateUserStepDefinitions {

    @When("I update an existing user")
    public void iUpdateAnExistingUser() {
        User user = user();
        theActorInTheSpotlight().attemptsTo(Create.withData(PATH_USER_CREATE,APP_ID).AndWith(user));
        User newUser = user();
        String userId = theActorInTheSpotlight().asksFor(GetUserId.ofResponse());
        theActorInTheSpotlight().remember("userId", userId);
        theActorInTheSpotlight().remember("newUser", newUser);
        theActorInTheSpotlight().attemptsTo(Update.withData(PATH_USER.concat(userId),APP_ID).AndWith(newUser));
    }
    @Then("I see new updated user data")
    public void iSeeNewUpdatedUserData() {
        String userId = theActorInTheSpotlight().recall("userId").toString();
        User newUser = theActorInTheSpotlight().recall("newUser");
        theActorInTheSpotlight().should(seeThat(GetLastResponse.ofResponse(), allOf(
                containsString(userId),
                containsString(newUser.getFirstName()),
                containsString(newUser.getLastName())
        )));
    }
    @When("I update user not existing")
    public void iUpdateUserNotExisting() {
        User user = user();
        theActorInTheSpotlight().attemptsTo(Update.withData(PATH_USER.concat(idIncorrect()),APP_ID).AndWith(user));
    }

}