class Song(object):
    
    def __init__(self, author, title, duration):
        self.author = author
        self.title = title
        self.duration = duration
        
    def __str__(self):
        return str(self.author + ' ' + self.title + ' ' + str(self.duration))