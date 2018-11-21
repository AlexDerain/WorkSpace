class Album(object):
    library = []
    
    def __init__(self, name):
        self.name = name
        self.songs = []
    
    def addSong(self, song):
        self.songs.append(song)
        self.library.append([song.author, song.title, self.name])
        
    def printAll(self):
        for song in self.songs:
            print(song)
            
    def checkAuthor(self, name):
        Album.tempLibrary = []
        for song in Album.library:
            if song[0] == name:
                Album.tempLibrary.append((song[1], song[2]))
        print(Album.tempLibrary)