# RES-Labo-04-SMTP

## Introduction

L'objectif de ce projet est de se familiariser avec le protocole SMTP et donc faire un programme pouvant envoyer des emails "prank" à un serveur SMTP (Donc faire un client SMTP).
 

### Email "prank" ? Qu'est-ce que c'est ?

L'utilisateur aura une liste de victimes (un fichier victimsList.txt avec une liste de emails) sur lesquelles il veut faire le prank. Il aura également un fichier messages.txt qui contient les messages pour le prank qui seront envoyées.
L'utilisateur choisira ensuite le nombre de groupe de victimes qu'il veut créer (dans chaque groupe il y aura toujours une victime qui envoit le mail et au moins 2 personnes qui vont le recevoir). Les messages du fichier messages.txt seront choisi au hasard pour chaque groupe.


### Je veux faire la blague mais ça m'a l'air compliqué ! Je fais comment ?

Avant de se lancer dedans il faut savoir que ce projet sert à un but purement académique et d'apprentissage. Il est ilégal par la loi d'envoyer des emails forgés ou encore de se faire passer pour quelqu'un d'autres. D'ailleurs vu que la plupart de fournisseurs internet bloquent les envois et que nous ne voulons pas surcharger un vrai serveur SMTP, nous allons utiliser un "Mock Server" SMTP.

### C'est quoi un "Mock Server" ?

Un mock serveur c'est tout simplement une simulation de serveur. On va simuler un serveur SMTP en local sur notre machine auquel on va envoyer nos emails.
Il en existe plusieurs sur internet et github. Pour notre projet nous avons décidé d'utiliser le serveur SMTP [MockMock](https://github.com/tweakers/MockMock) qui nous fourni une interface web bien pratique.
Donc pour la mise en place du Mock server il suffit donc de cloner le repos, faire la modification [suivante](https://github.com/tweakers/MockMock/pull/8/commits/fa4bea3079d88d7d7b9a28e3b0864ba6f3d9f7ff) dans le pom.xml (GoogleCode ayant fermé) et de lancer le programme avec votre IDE ou alors en téléchargent le jar directement sur leurs github.

### J'ai lancé MockMock mais rien ne se passse ?

MockMock utilisant une interface web, il faudra ouvrir votre navigateur et rentrer commen URL : **localhost:8282** (Le port http 8282 étant celui par défault dans MockMock).


### Ok, j'ai un serveur qui peut recevoir de mails, et le client ?

Pour le client il suffira de cloner ce repos et de le lancer depuis votre IDE préféré ou alors en lançant le .jar se trouvant dans le dossier release.

### Quelques explications sur le fonctionnement du code client.
Il y a 3 fichiers se trouvant dans le dossier **/config** qui peuvent être modifiés.

    - config.properties
    - messages.utf8
    - victimList.utf8


#### config.properties

Permet de choisir le serveur SMTP et le numéro de port auqel on veut se connecter ainsi que le nombre de groupe de victimes qu'on veut créer,

#### messages.utf8

Nous donne la liste des messages qui seront choisi aléatoirement pour chaque groupe.
Chaque message doit commencer avec un sujet avec la syntaxe suivante:  **Subject: <le sujet qu'on veut mettre>** suivi d'un retour à la ligne. Tout ce qui suit sera le contenu du message.
Pour la séparation entre les différents message nous utilisons la chaine de caractère **"=="**.

#### victimList.utf8

Nous donne la liste des emails qui seront utilisé lors de notre prank. Il suffit de séparer chaque email par un retour à la ligne.





### Descriptions des classes et implémentations du projet

#### Group

Un groupe est défini par une personne qui envoit le mail et une liste de personnes qui le reçoivent.

#### Mail

Un mail est défini par un sujet, un contenu, une personne qui envoit le mail et une liste de personnes qui l'envoient.

#### Message

Classe définissant un message. Un message est définit par un sujet et un contenu.

#### Person

Classe définissant une personne qui sera une des victimes.

#### PrankGenerator

Classe fournissant les fonctions pour générer notre prank
**Liste des fonctions : **

- List<Person> readVictimList(InputStream victims)
- List<Group> buildRandomGroups(List<Person> personList, int numberOfGroups)
- List<Mail> createRandomMails(List<Group> listOfGroups, InputStream messages)
- Message getRandomMessage(List<Message> messageList)
- List<Message> readMessageList(InputStream messages)

#### SmtpClient


