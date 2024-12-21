CREATE TABLE Cards(
CardID int,
Title varchar(25),
CardType varchar(10),
AP int,
MP int,
FP int,
laze int,
addturn int,
duration int,
FPCost int,
MPCost int,
qty int,
description varchar(50),
message varchar(80),
PRIMARY KEY (CardID)
);

INSERT INTO Cards(CardID, Title, CardType, AP, MP, FP, laze, addturn, duration, FPCost, MPCost, qty, description, message) VALUES 
(1, 'Book Reading', 'RED', 3, 0, 0, 0, 0, 1, 1, 0, 3, 'Gain 3 Academic Points', 'You Played Book Reading, Gained 3 AP'),
(2, 'Advanced Study','RED', 5, 0, 0, 0, 0, 1, 2, 0, 2, 'Use two slots, Gain 5 Academic Points', 'You Played AdvancedStudy, Gained 5 AP'), 
(3, 'Last Minute Review', 'RED', 1, 0, 0, 0, 0, 0, 0, 0, 2, 'Gain 1 Academic Poin', 'You Played Last Minute Review, Gained 1 AP'),
(4, 'Just A Peek', 'RED', 2, 0, 0, 0, 0, 0, 0, 2, 2, 'Just A Peek is fine right?', 'You Played Just a Peek, Gained 2 AP'),
(5, 'Procrastinate', 'RED', 7, 3, 0, 0, 0, 2, 2, 2, 1, 'Gain 7 AP', 'You chose to Procrastinate, Gained 3*laze AP'),
(6, 'Gaming Session', 'GREEN', 0, 4, 0, 2, 0, 2, 3, 0, 1, '+1 laze Stack, Restore 1 Mental Prowess', 'You Played Gaming Session, restored 2 MP'),
(7, 'Just One Game', 'GREEN', 0, 1, 0, 1, 0, 1, 1, 0, 2, '+1 laze Stack, Restore 1 Mental Prowess', 'You Played Just One Game, restored 1 MP'),
(8, 'On The Side', 'GREEN', 0, 1, 0, 0, 0, 0, 1, 0, 2, 'Restore 1 Mental Prowess, draw one card', 'You relaxed for a short while, restored 1 MP'),
(9, 'ShortNap', 'BLUE', 0, 0, 3, 0, 0, 1, 0, 0, 2, 'Restore 3 Focus Points', 'You took a Short Nap, Restored 2 FP'),
(10, 'Hot Shower', 'BLUE', 0, 0, 2, 0, 0, 1, 0, 0, 2, 'Restore 2 Focus Points', 'You took a Hot Shower, Restored 2 FP');