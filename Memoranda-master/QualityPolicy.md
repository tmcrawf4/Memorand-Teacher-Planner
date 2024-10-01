### Quality Policy
> Describe your Quality Policy in detail for this Sprint (remember what I ask you to do when I talk about the "In your Project" part in the lectures and what is mentioned after each assignment (in due course you will need to fill out all of them, check which ones are needed for each Deliverable). You should keep adding things to this file and adjusting your policy as you go.
> Check in Project: Module Concepts document on Canvas in the Project module for more details 

**GitHub Workflow** (start Sprint 1)
>The master branch is safeguarded and will only be merged at the conclusion of the sprint. We maintain a protected development branch, which also serves as a stable release branch. This is the branch we will merge into using a pull request. The Git master ensures that this merge occurs following team approval. The Git master notifies the team whenever the development branch is updated, prompting us to pull the branch and work on the latest version of memoranda. It is essential to pull the development branch each time it is updated. When a team member completes a task, they submit a pull request into the development branch, which the Git master approves (pending team approval). This approval typically involves testing to ensure the continued functionality of the application. Each team member can handle 2-3 tasks per sprint, totaling around 10-15 tasks collectively. Individual tasks are designed to be relatively short, achievable within a 2-4 hour timeframe. At the sprint's conclusion, we submit the pull request into the main branch, and this version of our software must be stable and as bug-free as possible (subject to testing).

To complete a User Story or task
>1. Make a new branch off of dev
>2. Implement the story / task
>3. Test the implementation
>4. Merge changes to dev

>Upon sprint completion, fast-forward master to dev.

**Unit Tests Blackbox** (start Sprint 2)  
  >Team members will create adequate test cases on their own branch that seek to cover all possible input, output and usage scenarios that can be produced within the program. These tests should focus on the functionality of a program's software, including testing the individual functions within a class along with the interfaces to ensure proper implementation.

 **Unit Tests Whitebox** (online: start Sprint 2, campus: start Sprint 3)  
  >Upon the creation of unit tests during blackbox testing, each team member will conduct an analysis of the code coverage for their assigned task's classes to prove the software's reliability and correctness. More unit tests will be implemented that seek to introduce deliberate faults or errors into the testing environment to verify that the test cases can detect these issues.

**Code Review** (online: start Sprint 2, campus: start Sprint 2)
  > Team members should conduct code reviews that aim to understand the logic of the source code, and identify any possible risk factors or practices that do not adhear to coding standards. The code review will include a table that clearly illustrates any possible complexities or functional errors that can be found within the program.

**Developer Checklist**
  > - Does the code have any build errors?
> - Have I followed proper coding standards?
> - Is the functionality complete and tested?
> - Have test been added to cover new functionality?

**Reviewer Checklist**
  > - Does the code run / build?
> - Are there any conflicts not shown by GitHub?
> - If yes, what is the severity of these conflicts?
> - Does the code adhere to the Task / User Story?

**Static Analysis**  (online: start Sprint 3, campus: start Sprint 3)
  > Your Static Analysis policy   

**Continuous Integration**  (start Sprint 3, campus: start Sprint 3)
  > Your Continuous Integration policy
