from Song import *
from Album import *

album1 = Album('good')
album2 = Album('bad')
song1 = Song('a', 'aa', 22)
song2 = Song('b', 'bb', 33)
song3 = Song('c', 'cc', 44)
song4 = Song('a', 'dd', 55)
album1.addSong(song1)
album1.addSong(song2)
album2.addSong(song3)
album2.addSong(song4)
album1.printAll()
album2.printAll()
print(Album.library)
album1.checkAuthor('a')
album2.checkAuthor('a')