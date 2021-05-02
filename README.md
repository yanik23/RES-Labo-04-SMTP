# RES-Labo-04-SMTP

## Introduction

L'objectif de ce projet est de se familiariser avec le protocole SMTP et donc faire un programme pouvant envoyer des emails "prank" aussi appellà un serveur SMTP.

### Email "prank" ? Qu'est-ce que c'est ?

L'utilisateur aura une liste de victimes (un fichier victimsList.txt avec une liste de emails) sur lesquelles il veut faire des blagues. Il aura également un fichier messages.txt avec les messages blagues qui seront envoyées.
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

### Quelques explications sur le fonctionnement du code client






