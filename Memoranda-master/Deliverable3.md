Deliverable Information


Topic: Teacher course planning tool

Sprint Number: 3

Scrum Master: Olivia Horwedel

Git Master: Tristan Crawford


1.
**Sprint Goal:** The goal of this sprint is to work to complete the implementation of visibility and assignment creation functionality, along with fixing bugs and inconsistency such as repeatable events not being recorded properly.

**How many User Stories did you add to the Product Backlog:** 
> 0 User Stories were added to the Product Backlog

**How many User Stories did you add to this Sprint:** 
> 13 User Stories were added to the Sprint

Sprint Planning Questions:

Why did you add these US, why do you think you can get them done in the next Sprint?
> These user stories were added to wrap up the unfinished features currently implemented in the program. Many of them are minor tweaks to existing features and should be easily completable. Some of the larger tasks may pose a challenge, but the team seems prepared to make a good attempt.

Why do you think these fit well with your Sprint goal? (details)
> These user stories fit well with the sprint goal as most of them focus on finishing up visibility and assignment functionality. Some unfinished user stories were also added, and these pertained to making visibility save and display properly for objects. Minor bugs and inconsistencies are also being addressed by fixing repeatable events and making visibility have a consistent menu. 

Do you have a rough idea what you need to do? (if the answer is no then please let me know on Slack) 
> Yes.

2. During the Sprint
Meeting minutes of your Daily Scrums (3 per week, should not take longer than 10 minutes):

| Date   | Who did NOT attend  | Meeting notes (very brief)    | Burndown Info | GitHub Actions info | Additional Info   |  
|--------|---------------------|-------------------------------|---------------|---------------------|-------------------|  
|2/16/24 |                     | Planning and user story creation           | No Burndown Progress. | 
|2/23/24 |                     | Quality policy and unit tests              | 14% | Build successful |
|2/24/24 |                     | Discussion of time management              | 14% | Build successful |
|2/26/24 |                     | Remaining tasks and progress               | 38% | Build successful |
|2/27/24 |                     | Merging PRs and completing documentation   | 93% | Build successful |

3.
Sprint Review:

Screencast link:
- [Screencast video](https://www.youtube.com/watch?v=4gFYrnUy4VM)

What do you think is the value you created this Sprint?

> The value created in this sprint was resonable. The new functionality for accounts and visibility maees the program more functional for a classroom setting. Each tier of user now only sees events and tasks that are applicable to them.


Do you think you worked enough and that you did what was expected of you?

> A fair amount of work was done and the sprint goal is mostly accomplished.


Would you say you met the customers’ expectations? Why, why not?

> As a final product, the customer's expectations are likely not met. However, when considering the requirements in the Topics document, it seems that the program is close to fully complete with valuable implementations for about 8 of the 10 requirements.


Sprint Retrospective:

Did you meet your sprint goal?

> The sprint goal was nearly met, with the exception of clearing all CheckStyle warnings. 

Did you complete all stories on your Sprint Backlog? 

> All user stories in Sprint 3 were completed with the exception of US90 Task91. 


If not, what went wrong?

> The massive amount of CheckStyle errors was too much for the assigned member to complete alone.


Did you work at a consistent rate of speed, or velocity? (Meaning did you work during the whole Sprint or did you start working when the deadline approached.)

> The rate of work was inconsistent, with almost all of the work being done within the final 5 days.


Did you deliver business value?

> Value was delivered, as the program should now function as an intuitive course planner.


Did you follow the Scrum process (e.g. move Tasks correctly?, keep the Taiga board up to date? work consistently?)

> The Scrum process was followed, and thhe Taiga board accurately represented the work being done on the project.


Are there things the team thinks it can do better in the next Sprint? (not needed for last Sprint)


How do you feel at this point? Get a pulse on the optimism of the team.


Starting in Sprint 2 Include a screenshot of your Burndown chart here and analyze in detail for me why it looks the way it does and how you could improve it if it needs improving.
![Screenshot 2024-02-28 014327](https://github.com/amehlhase316/Kopfkino_Spring24A/assets/147566773/c5d18d1d-9329-4a44-b953-69cd135518c5)

**Contributions:**

Team member -- Raul Silaghi:
**Do you think you individually worked consistently and put in enough work into the project (give a short answer).
Yes, I feel like a good amount of contribution was done on my end for this sprint.  I would have liked to do more, but due to personal issues I could not allocate more time to this Sprint.

**Links to GitHub commits (not PR) with main code contribution (up to 5 links) during the current Sprint:
- [Commit 1](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/c54a8b2bea6397330a50663c4bd122753bbdb925): created a JDialog in agenda called assignments
- [Commit 2](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/6b9253c818e342d7aa1bea07f3fee3603eceae6f): created a method to show all new assignments created to display in assignments
- [Commit 3](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/3f93e8156bb455875f5b6bdc9c05e3d9e907ff71): changed method from private to public to allow for testing
  
**GitHub links to your Pull Requests (up to 3 links) during the current Sprint:
- [Pull Request #64](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/64): US74 branch into dev branch, created Assignments jdialog in AgendaPanel.java
- [Pull Request #75](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/75): US74 branch into dev branch, created method to show all new assignments created from New Assignments to display in Assignments
- [Pull Request #88](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/88): US74 branch to dev branch, pulled to merge unit tests 

**GitHub links to your Unit Tests (up to 3 links) -- during current Sprint, start in Sprint 2 (everyone should write 4 good Unit Tests each Sprint):
- [AgendaPanelTest.java](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/3f93e8156bb455875f5b6bdc9c05e3d9e907ff71): created four unit tests to test save button, cancel button, table creation, and visibility

**GitHub links to your Code Reviews (up to 3 links) -- during current Sprint, start in Sprint 2:


**How did you contribute to Static Analysis -- during current Sprint, start in Sprint 2:
- [agendapanel.java](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/a22e626ec63dec375b5518441403f19f57a8dcc3): fixed formatting/indentations show in checkstyle

Team member -- Tristan Crawford:
**Do you think you individually worked consistently and put in enough work into the project (give a short answer).
Yes, I worked consistently throughout this sprint. However, I did run into some issues that slowed my progress such as build errors or mere conflicts that needed to be resolved, but I made sure to allocate time for my project and create a checklist of tasks that I wanted to complete by a certain time to ensure that my tasks were done in a timely manner.

**Links to GitHub commits (not PR) with main code contribution (up to 5 links) during the current Sprint:
- [Commit 1](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/7f1a67438e10c6509ab8747a9d98e2456fa2592d) - fixed topic and text element issues for US73
- [Commit 2](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/0a00c2e7011d8dc9b2bb85a4e49162c0a82e282f) - Code from US73 merges and successfully runs with US43
- [Commit 3](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/71bf5d745bc7c2cb47ead4e3afff874c9aa15490) - Visibilty implementation of US43 works properly

**GitHub links to your Pull Requests (up to 3 links) during the current Sprint:  
-[Pull Request 90](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/90) - Implements the refactored code from US73 into US43  
-[Pull Request 87](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/87) - US43 implementation of visibility based on rank

**GitHub links to your Unit Tests (up to 3 links) -- during current Sprint, start in Sprint 2 (everyone should write 4 good Unit Tests each Sprint):


**GitHub links to your Code Reviews (up to 3 links) -- during current Sprint, start in Sprint 2:


**How did you contribute to Static Analysis -- during current Sprint, start in Sprint 2:
-[Statis Analysis 1](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/d31cf3f6f35e78879f0615cc38d485b6195245b5) - removal of checkstyle errors for my user stories



Team member -- Jack Featherstone:
**Do you think you individually worked consistently and put in enough work into the project (give a short answer).

> I was not able to work as consistently as I wanted to. The first week of the sprint I felt compelled to prioritize Assignment 5b and only began my tasks after I had finished it. Once I began working however, I felt that I worked quite consistently and pushed new features at least once every day / two days. I believe I did plenty of work on coding for this sprint.


**Links to GitHub commits (not PR) with main code contribution (up to 5 links) during the current Sprint:
- [Commit 1](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/2c1e5e84d7244e9f9e59696699796a0dec1f15ae) - US72: Make the Visibility selectors consistent 
- [Commit 2](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/0aeaa0e7dc5b183a95bf5e7e5be641a50180a97d) - US82: TASK85 - Update Tasks to save the Visibility choice
- [Commit 3](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/81807354bff5bb8e1a87b5d1f9eb9a9269e28678) - US92: TASK94 - Make Visibility ComboBox in edit Event dialog show correct value
- [Commit 4](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/384658bf775b7e6fd831c635eb0ac5fb2e910d77) - US95: TASK96 - Add Visibility column for To-Do list items 
- [Commit 5](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/43892252f1f21a9932f243c6019947dc03b8ec30) - US47: TASK49 - Make each Task display only for users of the appropriate permissions level

**GitHub links to your Pull Requests (up to 3 links) during the current Sprint:
- [Pull Request #62](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/62) - US72: Make the Visibility selectors consistent
- [Pull Request #67](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/67) - US82: Implement Visibility Selectors
- [Pull Request #68](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/68) - US92: Make Visibility ComboBoxes show correct value in Edit Dialogs

**GitHub links to your Unit Tests (up to 3 links) -- during current Sprint, start in Sprint 2 (everyone should write 4 good Unit Tests each Sprint):
- [Unit Tests 1, 2, & 3](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/f2e351a3d5d705f67176aca24ade54f389b89e94) - Tests for new features in US82  
- [Unit Test 4](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/efbe67f17f1e98f2c7ee8b1a1718ec9914e6b580) - Test for new feature in US47

**GitHub links to your Code Reviews (up to 3 links) -- during current Sprint, start in Sprint 2:
- [Code Reviews](https://github.com/amehlhase316/Kopfkino_Spring24A/tree/jrfeathe-reviews/REVIEWS)

**How did you contribute to Static Analysis -- during current Sprint, start in Sprint 2:
- [App.java](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/971c246ac7d729db9a6269d5f9306cf390eca8af)


Team member -- Hunter Fields:
**Do you think you individually worked consistently and put in enough work into the project (give a short answer).
I felt as though I started probably a little late but worked consistenetly, got a solid amount done, and put in enough work during this sprint. I felt like I could have given more if there were not a lot of other assignments going on at the same time.

**Links to GitHub commits (not PR) with main code contribution (up to 5 links) during the current Sprint:
- [Commit 1](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/88ab436820be69543c7ad9594b57798cca1b2298) - US40: TASK79 - Add ability to more easily create holidays, free days, lectures, exams as events
- [Commit 2](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/2126e4a70a8dd3c12b81e69cc3fd245cb8ca48d8) - US86: TASK87 - Enable SpotBugs in build.gradle file
- [Commit 3](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/dde36d93ff9400431cf1099e0d654f9311a2603f) - US40: TASK79 - Added drop down menu for quick options.

**GitHub links to your Pull Requests (up to 3 links) during the current Sprint:
- [Pull Request #71](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/71) - US40 - Can more easily add holidays, lectures, exams, free days as events.
- [Pull Request #61](https://github.com/amehlhase316/Kopfkino_Spring24A/pull/61) - US86- Spotbugs enabled, gradle build suceeds with updated spotbugs version.

**GitHub links to your Unit Tests (up to 3 links) -- during current Sprint, start in Sprint 2 (everyone should write 4 good Unit Tests each Sprint):
- [QuickFillTopicBox.java](https://github.com/amehlhase316/Kopfkino_Spring24A/blob/hafields-test/src/test/java/QuickFillTopicBox.java) - Testing the addition of the new drop down menu quick add options.

**GitHub links to your Code Reviews (up to 3 links) -- during current Sprint, start in Sprint 2:
- [Code Reviews](https://github.com/amehlhase316/Kopfkino_Spring24A/blob/hafields-review/CodeReview.md)

**How did you contribute to Static Analysis -- during current Sprint, start in Sprint 2:
- [Commit 1](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/a1a437734eaf6ce50d7d8bdf40f5dbae8e783779) -  Brought high priority Spot-Bug warnings down to 28. 
- [Commit 2](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/64a5724186d55606b575f9628220ee1d5ec30fb9) -  Removed boxing unboxing high priority issues.
- [Commit 3](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/fd5bd4b8cd4d8595a6b2c22c77528b44dd41964e) - Fixed high priority null pointer references in spotbugs.
- [Commit 4](https://github.com/amehlhase316/Kopfkino_Spring24A/commit/9c96e83bdbfad7a716395c3f08b4006d220f4e4e) - Finished removing all high priority warnings in spotbugs.

Team member -- Matt Anderson:
**Do you think you individually worked consistently and put in enough work into the project (give a short answer).
I did not work consistently, however I participated in team meetings/chats and was available to discuss things during the Sprint.  I do believe I put in enough work this sprint and was a valuable team member.

**Links to GitHub commits (not PR) with main code contribution (up to 5 links) during the current Sprint:
https://github.com/amehlhase316/Kopfkino_Spring24A/commit/cfc3c719ec82f007b6117fa120a0487595fa21e8

**GitHub links to your Pull Requests (up to 3 links) during the current Sprint:
https://github.com/amehlhase316/Kopfkino_Spring24A/pull/85

**GitHub links to your Unit Tests (up to 3 links) -- during current Sprint, start in Sprint 2 (everyone should write 4 good Unit Tests each Sprint):
n/a
**GitHub links to your Code Reviews (up to 3 links) -- during current Sprint, start in Sprint 2:
n/a

**How did you contribute to Static Analysis -- during current Sprint, start in Sprint 2:
We had several discussions on Discord on ways we wanted to solve certain coding challenges.

Team member -- Olivia Horwedel:
**Do you think you individually worked consistently and put in enough work into the project (give a short answer).



**Links to GitHub commits (not PR) with main code contribution (up to 5 links) during


**GitHub links to your Pull Requests (up to 3 links) during the current Sprint:


- **GitHub links to your Unit Tests (up to 3 links) -- during current Sprint, start in Sprint 2 (everyone should write 4 good Unit Tests each Sprint):


**GitHub links to your Code Reviews (up to 3 links) -- during current Sprint, start in Sprint 2:


**How did you contribute to Static Analysis -- during current Sprint, start in Sprint 2:



**GitHub links to your Unit Tests (up to 3 links) -- during current Sprint, start in Sprint 2 (everyone should write 4 good Unit Tests each Sprint):



**GitHub links to your Code Reviews (up to 3 links) -- during current Sprint, start in Sprint 2:



**How did you contribute to Static Analysis -- during current Sprint, start in Sprint 2:
