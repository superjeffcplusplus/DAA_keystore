# Introduction à *Android Keystore System*

Android met à disposition un outil spécifique dédié au stockage des données sensibles, le *Keystore System*.  A son apparition dans la version 1.6 d'Android, il n'était prévu que d'offrir une protection particulière aux clés de chiffrement utilisées dans les communications Wifi d'une part et dans les communications VPN d'autre part. Toutefois, depuis la version 4.0 du système d'exploitation pour mobiles (API level 14), ce module est ouvert à toutes les applications nécessitant de chiffrer des données. C'est utile non seulement pour les clefs cryptographiques, mais aussi pour les fichiers sensibles, peut importe son type.  
A partir de l'*API level 28*, le système proposé par Android peut s'appuyer sur les modules hardware dédiés à la gestions des clefs cryptographiques similaires au *Trusted Platform Module* (TPM) que l'on trouve désormais sur tous les PC récents.  
## But de la fonctionnalité
L'idée générale est **d'une part** de rendre l'extraction de l'appareil des clefs de chiffrement ou de signature cryptographique très difficile, même dans le cas où un attaquant aurait un accès physique complet à l'appareil de sa victime. Typiquement, un TPM permet de générer des clefs. Il dispose d'une API permettant de chiffrer et de déchiffrer des données avec du chiffrement asymétrique ou symétrique, ou d'effectuer des signatures cryptographiques.  
Mais rien n'est prévu pour extraire les clefs privées ou symétriques. Au contraire, tout est fait pour que cela soit rendu très difficile. Le *Keystore* est conçu pour offrir des caractéristiques similaires, tout en sachant que du harware dédié est indispensable si on a la nécessité d'un niveau maximal de protection.  
**D'autre part**, le *Keystore* gère les droits d'utilisation du matériel crryptographique selon le principe de la *white list*. Cela veut dire que seule les applications explicitement autorisées à l'utilisation d'une certaine clef y on accès. Typiquement, si une application `A` chiffre des données en passant par le *Keystore*, alors une application `B` ne pourra pas accéder aux clefs utilisées par l'application `A`, sauf en cas d'autorisation explicite. 

## Problème résolu et limitations
Grâce au *Keystore*, le développeur d'application Android n'aura qu'à ajouter quelques lignes de codes pour chiffrer des données. Il aura à choisir un algorithme parmi un choix limité. En outre, l'API d'utilisation du Keystore est faite de telle manière à ce que 

- choix limité des algorithmes 
## Alternatives

## Example d'utilisation

## Liens utiles

- [Documentation officielle sur le Keystore System](https://developer.android.com/training/articles/keystore#kotlin)
- [Documentation officielle sur le hardware dédié](https://source.android.com/docs/security/features/keystore)