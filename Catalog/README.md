Munteanu Alexandru 321CC
README pentru tema la POO
Timp de implementare: 40-50 de ore
Enuntul a fost complet implementat

Dificultate: Medie: cea mai dificila parte a fost parsarea fisierelor json,
dar si crearea unor teste.


!!!Pentru testare am folosit doar 2 grupe AAA, respectiv ABC, pentru a nu
aglomera fisierul json, dar si afisarile din consola folosite pentru Debug!!!
!!!Recomandare pentru testare: TeacherPage: Georgescu Victor 
                                ParentPage: Munteanu Ramona/ Munteanu Ionut
                                StudentPage: Munteanu Alexandru
Continutul proiectului:

Test : fisier de test FARA citire din fisier
CitireTeste : fisier de test CU citire din fisier
*Page : fisiere care construiesc interfata grafica
GradesToValidate : fisier care arata notele care nu sunt validate la un curs
Details: detalii despre notele unui student
App: deschiderea aplicatiei cu datele oferite in fisierul cursuri.json
Restul: fisiere din cerinta

Detalii:

Pentru a construi interfaca grafica m-am folosit de o fereastra intitulata 
"Master Page", care ii da utilizatorului optiunea de a alege dintre cele
3 tipuri de pagini; Student, Parent si Teacher. Cele 3 pagini sunt asemanatoare,
toate avand Flowlayout si cerand numele unui utilizator in formatul: 
lastName + " " + firstName. Pentru a cauta un utilizator in catalog se apasa pe butonul
de Search. Daca utilizatorul este gasit, se va afisa o alta pagina care au continutul
specificat in cerinta. Pentru fiecare utilizator layout-ul paginii este Grid Layout, in
care sunt afisate listele de cursuri pentru student si profesor si o pagina simpla cu 
notificari pentru parinte. Listele de cursuri au implementate MouseListener, astel ca daca 
utilizatorul doreste sa afle mai multe detalii despre curs va trebui sa dea 2xclick pe
cursul dorit.

!!!Butonul de validare a notelor valideaza toate notele indiferent de curs!!!

!Important! Inchiderea ferestrei "Master" va duce la inchiderea programului, celelate pagini
nu duc la inchiderea programului, putand functiona independent una de cealalta.

Programul nu are functionalitati in plus, dar pe parcursul rezolvarii temei, am adaugat 
in fiecare clasa, in functie de nevoi, anumite metode pentru a seta sau a primi informatii.

In CourseBuilder, toate metodele sunt abstracte, urmand a fi implementate in FullCourse,
respectiv PartialCourse. Toata campurile mai complexe din Course, cand este folosit
builder, VA CREA campul, nu va adauga in acel camp. Un exemplu este campul assistants,
cand la apelarea .assistant() se va face assistant = new ... .

Pentru validare, am presupus ca un asistent poate introduce doar nota "partialScore", 
iar un profesor poate introduce doar examGrade, de aceea, in "note.json", este 
prezent doar un camp "Grade". Daca un elev are deja o nota, aceasta va fi suprascrisa
la validare.

In clasa Group, daca este primit un comparator, studentii vor fi ordonati, dupa
criteriul comparatorului.

Clasa care va implementa Observer este clasa parent, deoarece parintii vor dori
sa "observe" notele unui student.
Clasa care implementeaza interfata Subject este clasa Catalog, deoarece aceasta
reprezinta "subiectul" observarii.

Clasa User va implementa Element.

!!Nu pot exista doua note pentru acelasi student!!

Daca este adaugata o nota, dar studentul deja exista, se vor suprascrie "partialScore"
si "examScore".

Clasa ScoreVision:
createStudent : creeaza un stundent, bazandu-se pe informatiile parsate din fisierul json
readFile: parseaza un fisier json si construieste obiectele necesare
functiile visit din cerinta.