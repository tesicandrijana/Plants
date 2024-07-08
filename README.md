# Dokumentacija za aplikaciju za biljke

## Opis

Aplikacija za upravljanje biljkama je Android aplikacija dizajnirana da olakša korisnicima istraživanje biljaka koje koje korisnici uzgajaju, planiraju uzgajati ili o kojim samo žele znati više. Cilj aplikacije je da pruži korisnicima informacije o biljkama kako bi poboljšali svoje vrtlarske sposobnosti, kao i znanje o biljkama.

## Funkcionalnosti aplikacije

### Pregled Biljaka

- Aplikacija omogućava korisnicima da pregledaju različite vrste biljaka prikazujući njihove osnovne informacije kao što su naziv i slike
- Korisnici mogu odabrati koju grupu podataka žele pregledati. Moguće grupe su: otrovne, jestive, unutrašnje i vanjske biljke. Takođe u tim grupama mogu filtrirati podatke po tome koliko često je potrebno zalijevati biljku (minimalno, prosječno ili redovno), ili mogu odabrati da se podaci prikazuju bez filtera.

### Detalji o Biljkama

- Za svaku biljku dostupni su detaljni podaci kao što su zahtjevi za zalivanjem, svjetlošću, tipične bolesti i štetočine, da li je otrovna za ljude ili životinje, ima li cvijetove, ima li voće, itd..
- Na svakom ekranu sa detaljima o biljkama moguće je sačuvati tu biljku, ili ukloniti iz sačuvanih biljaka.

### Upravljanje Omiljenim Biljkama

- Korisnici mogu sačuvati svoje omiljene biljke radi lakšeg pristupa i organizacije.
- Mogućnost dodavanja, uklanjanja ili uređivanja biljaka u listi omiljenih, kao i pregled liste sačuvanih biljaka.

## Arhitektura aplikacije

### Sloj za pristup podacima (Data Access Layer)

#### Klase i interfejsi

**AppContainer (interfejs)**:
- Definiše zavisnosti potrebne aplikaciji, kao što je plantsRepository koji omogućava pristup podacima o biljkama.

**DefaultAppContainer (klasa)**:
- Implementira AppContainer i pruža konkretne implementacije zavisnosti.
- Inicijalizira Retrofit za komunikaciju sa udaljenim API-jem.
- Konfiguriše OkHttpClient sa različitim interceptorima za keširanje i upravljanje mrežnim zahtjevima.
- Pruža plantsRepository koji se koristi za pristup podacima o biljkama putem Retrofit servisa i lokalne Room baze podataka.

**CacheInterceptor (klasa)**:
- Interceptor za dodavanje Cache-Control zaglavlja u HTTP odgovore radi keširanja.

**ForceCacheInterceptor (klasa)**:
- Interceptor koji forsira keširanje ako nema dostupne internet konekcije.

**NetworkUtils (objekat)**:
- Helper funkcija za provjeru dostupnosti internet konekcije.

**Converters (objekat)**:
- Sadrži funkcije za konverziju kompleksnih objekata (kao što su listovi i custom objekti) u JSON stringove i obrnuto, neophodne za rad sa Room bazom podataka.

**PlantDetailDao (interfejs)**:
- DAO (Data Access Object) koji definiše SQL operacije za manipulaciju podacima o biljkama u lokalnoj Room bazi podataka.
- Uključuje operacije poput inserta, brisanja i upita za dohvatanje podataka o biljkama.

**PlantsDatabase (klasa)**:
- Room baza podataka koja nasljeđuje RoomDatabase.
- Definiše singleton instancu baze sa metodama za dobijanje PlantDetailDao-a.
- Koristi se za inicijalizaciju i upravljanje lokalnom bazom podataka biljaka.

**PlantsRepository (interfejs)**:
- Definiše operacije za pristup podacima o biljkama, koje su implementirane i u lokalnoj bazi i preko udaljenog API-ja.
- Sadrži funkcije kao što su dobijanje svih biljaka, detalja o biljci po ID-u, ubacivanje i brisanje biljke.

**NetworkPlantsRepository (klasa)**:
- Implementacija PlantsRepository koja koristi Retrofit servis za dobijanje podataka o biljkama preko udaljenog API-ja i Room DAO za lokalne operacije sa podacima.

**PlantsApiService (interfejs)**:
- Retrofit servis za definisanje API endpoints za dobijanje podataka o biljkama.
- Sadrži endpoint za dobijanje otrovnih, jestivih, unutrašnjih i vanjskih biljaka, kao i detalja o pojedinačnoj biljci.

#### Dodatne klase

- **PlantListResponse, Plant, Image, PlantDetail, Dimensions, WaterRequirement, WateringGeneralBenchmark, PlantAnatomy, Hardiness, HardinessLocation**: Kotlin data klase koje predstavljaju podatke o biljkama i njihove karakteristike. Koriste se za serijalizaciju i deserijalizaciju JSON podataka iz API odgovora.

### UI Sloj

#### Komponente

**PlantsNavHost (Composable funkcija)**:
- NavHost komponenta za navigaciju između različitih ekrana u aplikaciji.
- Koristi NavHostController za upravljanje navigacijom.
- Scaffold komponenta pruža osnovnu strukturu sa PlantsAppBar-om na vrhu ekrana.
- Sadrži composable funkcije za prikaz početnog ekrana (HomeScreen), galerije biljaka (PlantGalleryScreen), detalja biljke (PlantDetailScreen), i sačuvanih biljaka (SavedScreen).

**PlantsAppBar (Composable funkcija)**:
- Prikazuje app top bar sa naslovom trenutnog ekrana.
- Omogućuje navigaciju unazad i dijeljenje sadržaja.

**HomeScreen (Composable funkcija)**:
- Prikazuje početni ekran s opcijama za istraživanje i pregled sačuvanih biljaka.

**PlantGalleryScreen (Composable funkcija)**:
- Prikazuje listu grupe biljaka koju je odabrao korisnik.
- Sadrži dropdown meni za odabir grupe i filtriranje podataka, composable funkciju za prikaz biljaka u listi (ili gridu za horizontalni layout) i composable funkciju za kartice u listi.

**PlantDetailScreen (Composable funkcija)**:
- Prikazuje detalje o odabranoj biljci, uključujući sliku, opis i karakteristike.
- Omogućuje korisniku da spremi ili ukloni biljku s liste omiljenih.
- Sadrži composable funkciju Information koja prikazuje detaljne informacije o biljci, kao što su naučna imena, opis, porijeklo, dimenzije, zahtjevi za zalijevanje, anatomija, uslovi rasta, i druge karakteristike.

**SavedScreen (Composable funkcija)**:
- Prikazuje spremljene biljke korisnika, omogućujući navigaciju do detalja svake biljke. Ako je layout vertikalan biljke se prikazuju u listi, a ako je layout horizontalan biljke se prikazuju u gridu.

**LoadingScreen (Composable funkcija)**:
- Prikazuje ekran učitavanja s porukom "Loading".

**ErrorScreen (Composable funkcija)**:
- Prikazuje poruku o grešci i omogućava ponovni pokušaj dohvatanja podataka klikom na "Retry" dugme.

#### Ostalo

**NavigationDestination (interface)**:
- Definira destinacije za navigaciju u aplikaciji, poput ruta i naslova ekrana.

**HomeDestination (object)**:
- Implementacija NavigationDestination za početni ekran aplikacije.

### View Modeli

**PlantDetailViewModel**
- PlantDetailViewModel je odgovoran za upravljanje prikazom detalja biljke u aplikaciji. Glavni zadaci ovog view modela uključuju:
  - Dobavljanje podataka: Koristi se za dobavljanje detalja o određenoj biljci sa serverskog izvora preko plantsRepository-ja. Koristeći funkciju isPlantSaved se provjerava da li je biljka već sačuvana. Ukoliko odabrana biljka nije sačuvana podaci se dohvataju pomoću api-ja.
  - Skladištenje stanja: Održava trenutno stanje prikaza biljke, uključujući informacije o tome da li je biljka sačuvana kao omiljena.
  - Interakcija sa korisničkim interfejsom: Pruža funkcionalnosti za čuvanje ili uklanjanje biljaka iz liste omiljenih.

**PlantsViewModel**
- PlantsViewModel upravlja prikazom liste biljaka u aplikaciji i omogućava korisnicima da istražuju različite grupe biljaka i filtriraju podatke. Ključne funkcije uključuju:
  - Dobavljanje podataka: Koristi se za dobavljanje listi biljaka iz plantsRepository-ja na osnovu odabrane grupe (npr. otrovne, jestive, za unutrašnju ili spoljašnju upotrebu) i primenjenog filtera.
  - Skladištenje stanja: Održava trenutno stanje prikaza liste biljaka, uključujući odabranu grupu i primenjeni filter.
  - Interakcija sa korisničkim interfejsom: Pruža metode za promjenu odabrane grupe i filtera, što omogućava korisnicima da personalizuju prikaz prema svojim interesovanjima.

**AppViewModelProvider**
- AppViewModelProvider pruža ViewModelProvider.Factory za aplikaciju, što omogućava pravilno upravljanje životnim ciklusom PlantsViewModel-a i PlantDetailViewModel-a unutar aplikacije. Glavne funkcionalnosti uključuju:
  - Inicijalizacija: Pruža inicijalizaciju za PlantsViewModel i PlantDetailViewModel, omogućavajući im da pravilno interaguju sa plantsRepository-jem.
  - Upravljanje zavisnostima: Omogućava efikasno upravljanje zavisnostima između view modela i ostalih komponenti aplikacije.

**PlantsApplication**
- PlantsApplication nasljeđuje klasu Application i koristi se kao globalni kontekst za cijelu Android aplikaciju. Glavna svrha ove klase je da pruži tačku ulaska pri pokretanju aplikacije i omogući inicijalizaciju neophodnih komponenti. Ključne karakteristike uključuju:
  - `container` atribut: container je objekat tipa AppContainer koji se koristi za upravljanje zavisnostima (dependency injection) u aplikaciji. Ovaj atribut je označen kao `lateinit`, što znači da će se inicijalizirati nakon kreiranja instance klase PlantsApplication.
  - `onCreate` metoda: Ova metoda je preklopljena (overridden) iz klase Application i poziva se prilikom pokretanja aplikacije. U njoj se prvo poziva `super.onCreate()` radi izvršavanja osnovne inicijalizacije iz roditeljske klase, a zatim se kreira DefaultAppContainer objekat i dodeljuje atributu `container`.
  - Inicijalizacija zavisnosti: Korišćenjem DefaultAppContainer, klasa PlantsApplication vrši inicijalizaciju zavisnosti potrebnih za rad aplikacije. Ovo uključuje sve komponente kao što su repozitoriji, baze podataka ili druge servise neophodne za izvršavanje poslovnih logika aplikacije.

## Opšti koncepti Android frameworka

### Aktivnosti (Activities)

Aktivnosti su osnovni gradivni blokovi korisničkog interfejsa u Android aplikacijama. One predstavljaju jedan ekran. Svaka aktivnost je predstavljena Activity klasom, koja upravlja životnim ciklusom aktivnosti, događajima korisničkog interfejsa, i navigacijom između ekrana.

#### Životni ciklus aktivnosti

Aktivnosti imaju životni ciklus koji se sastoji od sljedećih faza:
- `onCreate()`: Poziva se kada se aktivnost prvi put kreira. Ovo je mesto gde se obavlja inicijalna postavka, kao što je definisanje korisničkog interfejsa.
- `onStart()`: Aktivnost postaje vidljiva korisniku.
- `onResume()`: Aktivnost počinje interakciju sa korisnikom.
- `onPause()`: Aktivnost prelazi u pozadinu, ali je još uvek vidljiva.
- `onStop()`: Aktivnost više nije vidljiva korisniku.
- `onDestroy()`: Aktivnost je završena i resursi su oslobođeni.

### Fragmenti (Fragments)

Fragmenti su modularni dijelovi aktivnosti koji omogućavaju razdvajanje korisničkog interfejsa u manje, ponovo upotrebljive delove. Fragmenti imaju sopstveni životni ciklus koji je integrisan sa životnim ciklusom aktivnosti.

### Komponente za navigaciju (Navigation Components)

Android Jetpack Navigation Components pružaju jednostavan način za implementaciju navigacije unutar aplikacije. To uključuje navigacioni grafikon (navigation graph) koji definiše moguće putanje navigacije i kontroler za navigaciju (NavController) koji upravlja navigacijom između fragmenata i aktivnosti.

### Compose

Jetpack Compose je alatka za pravljenje korisničkog interfejsa u Androidu. Umesto tradicionalnog XML dizajna, Compose koristi Kotlin i deklarativan pristup za definisanje korisničkog interfejsa.

#### Osnovne Compose komponente

- `Text`
- `Button`
- `Row`
- `Column`
- `Image`

#### State Management u Compose

Jetpack Compose koristi reaktivni pristup za upravljanje stanjem. `State` i `MutableState` se koriste za održavanje i ažuriranje stanja koje utiče na korisnički interfejs.

### LiveData i StateFlow

- **LiveData**: Klasa koja prati podatke i omogućava komponentama korisničkog interfejsa da prate promjene tih podataka.
- **StateFlow**: Kotlin Flow varijanta koja se koristi za emitovanje trenutnog stanja i omogućava ažuriranje korisničkog interfejsa.

### Room Persistence Library

Room je biblioteka za perzistenciju podataka koja olakšava rad sa SQLite bazom podataka u Android aplikacijama.

### Retrofit biblioteka

Retrofit je biblioteka za Android (i Java/Kotlin) koja olakšava rad sa RESTful API-ima. Ona omogućava definisanje API servisa kao interfejsa i automatsku serializaciju/deserializaciju podataka u JSON formatu. Glavne karakteristike Retrofit-a uključuju:
- Definisanje servisa: Koristite anotacije poput `@GET`, `@POST`, itd., za definisanje HTTP metoda i endpointa.
- Integracija sa Gson/Moshi: Automatska konverzija JSON odgovora u Kotlin objekte i obrnuto.
- Interceptori: Omogućava dodavanje interceptora za obradu mrežnih zahtjeva, kao što su logiranje, keširanje ili dodavanje zaglavlja.
- RxJava/Coroutines podrška: Omogućava integraciju sa reaktivnim pristupom, kao što su korutine u Kotlin-u, za asinkroni rad.

### Dependency Injection (DI)

Dependency Injection (DI) je tehnika koja olakšava upravljanje zavisnostima između različitih komponenti aplikacije.
