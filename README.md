# Inicijalne upute za prijavu projekta iz Razvoja aplikacija za mobilne i pametne uređaje

Poštovane kolegice i kolege, 

čestitamo vam jer ste uspješno prijavili svoj projektni tim na kolegiju Razvoj aplikacija za mobilne i pametne uređaje, te je za vas automatski kreiran repozitorij koji ćete koristiti za verzioniranje vašega koda i za jednostavno dokumentiranje istoga.

Ovaj dokument (README.md) predstavlja **osobnu iskaznicu vašeg projekta**. Vaš prvi zadatak je **prijaviti vlastiti projektni prijedlog** na način da ćete prijavu vašeg projekta, sukladno uputama danim u ovom tekstu, napisati upravo u ovaj dokument, umjesto ovoga teksta.

Za upute o sintaksi koju možete koristiti u ovom dokumentu i kod pisanje vaše projektne dokumentacije pogledajte [ovaj link](https://guides.github.com/features/mastering-markdown/).
Sav programski kod potrebno je verzionirati u glavnoj **master** grani i **obvezno** smjestiti u mapu Software. Sve artefakte (npr. slike) koje ćete koristiti u vašoj dokumentaciju obvezno verzionirati u posebnoj grani koja je već kreirana i koja se naziva **master-docs** i smjestiti u mapu Documentation.

Nakon vaše prijave bit će vam dodijeljen mentor s kojim ćete tijekom semestra raditi na ovom projektu. Mentor će vam slati povratne informacije kroz sekciju Discussions također dostupnu na GitHubu vašeg projekta. A sada, vrijeme je da prijavite vaš projekt. Za prijavu vašeg projektnog prijedloga molimo vas koristite **predložak** koji je naveden u nastavku, a započnite tako da kliknete na *olovku* u desnom gornjem kutu ovoga dokumenta :) 

# Stanarko

## Projektni tim


Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Robert Vrđuka | rvrduka20@student.foi.hr | 0016148751 | rvrduka20 | G02
Danijel Žebčević | dzebcevic20@foi.hr | 0016149470  | DanijelZebcevic | G02
Matija Tomašić | mtomasic20@student.foi.hr | 0016149486 | mtomasic2 | G02
Ivo Rošić | irosic20@student.foi.hr | 0016151299 | irosic20 | G02

## Opis domene
Aplikacija će biti namijenjena stanodavcima i stanarima tih objekata. Aplikacije će omogućiti stanodavcima unošenje novih stanova. Aplikacija će također omogućiti stanodavcima i stanarima objekata upravljanjem računima stanarine, te prijavom kvarova i slanjem obavijesti o remećenju javnog mira.

## Specifikacija projekta
Umjesto ovih uputa opišite zahtjeve za funkcionalnošću mobilne aplikacije ili aplikacije za pametne uređaje. Pobrojite osnovne funkcionalnosti i za svaku naznačite ime odgovornog člana tima. Opišite osnovnu buduću arhitekturu programskog proizvoda. Obratite pozornost da mobilne aplikacije često zahtijevaju pozadinske servise. Također uzmite u obzir da bi svaki član tima trebao biti odgovoran za otprilike 3 funkcionalnosti, te da bi opterećenje članova tima trebalo biti ujednačeno. Priložite odgovarajuće dijagrame i skice gdje je to prikladno. Funkcionalnosti sustava bobrojite u tablici ispod koristeći predložak koji slijedi:

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Registracija | Korisnik će se morati registrirati kako bi mogao pristupiti sustavu. | ...
F02 | Prijava | Da bi korisnik mogao koristiti aplikaciju mora se prvo prijaviti u sustav. | ...
F03 | Unos i brisanje stanova | Sustav će omogućiti dodavanje i brisanje novih stanova na popis dostupnih. | Danijel Žebčević
F04 | Generiranje računa | Korisnik će moći kreirati račun koji drugi korisnik mora platiti. | Matija Tomašić
F05 | Plaćanje računa | Nakon što stanodavac kreira račun stanar će moći platiti račun pomoću sustava. | Matija Tomašić
F06 | Grafički prikaz stanara | Sustav će omogućiti korisniku pregled rezervacija svojih stanova. | ...
F07 | Dodavanje ljudi u stan | Stanodavac će moći dodati nove stanare u svoj stan. | Danijel Žebčević
F08 | Planiranje selidbe | Sustav će omogućiti korisniku da rezervira datum kad se želi useliti u stan. | Danijel Žebčević
F09 | Komuniciranje pomoću maila | Stanar i stanodavac će moći komunicirati pomoću aplikacije. | Matija Tomašić
F10 | Stvaranje događaja | Korisnik će moći kreirati događaj u svome stanu koje će ostali korisnici moći vidjeti. | ...
F11 | Kreiranje ugovora | Sustav će omogućiti kreiranje ugovora između stanodavca i stanara. |...
F12 | Prijava kvarova | U slučaju da korisnik ima kvar u stanu, moći će ga prijaviti u aplikaciji. |...


## Tehnologije i oprema
Umjesto ovih uputa jasno popišite sve tehnologije, alate i opremu koju ćete koristiti pri implementaciji vašeg rješenja. Vaše rješenje može biti implementirano u bilo kojoj tehnologiji za razvoj mobilnih aplikacija ili aplikacija za pametne uređaje osim u hibridnim web tehnologijama kao što su React Native ili HTML+CSS+JS. Tehnologije koje ćete koristiti bi trebale biti javno dostupne, a ako ih ne budemo obrađivali na vježbama u vašoj dokumentaciji ćete morati navesti način preuzimanja, instaliranja i korištenja onih tehnologija koje su neopbodne kako bi se vaš programski proizvod preveo i pokrenuo. Pazite da svi alati koje ćete koristiti moraju imati odgovarajuću licencu. Što se tiče zahtjeva nastavnika, obvezno je koristiti git i GitHub za verzioniranje programskog koda, GitHub Wiki za pisanje jednostavne dokumentacije sukladno uputama mentora, a projektne zadatke je potrebno planirati i pratiti u alatu GitHub projects.

## Baza podataka i web server
Nastavnici vam mogu pripremiti MySQL bazu podataka i web server na kojem možete postaviti jednostavne php web servise. Ako želite da vam pripremimo ove sustave obavezno to navedite umjesto ovog teksta s napomenom "Trebamo bazu podataka i pristup serveru za PHP skripte". Alternativno, možete koristiti bilo koji online dostupan sustav kao i studentske licence na pojedinim platformama kao što su Heroku ili Azure.

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
