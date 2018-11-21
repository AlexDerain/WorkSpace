class Shape:
    def __init__(self):
        self.x = 0
        self.y = 0
    
    def getXYLoc(self):
        return self.x, self.y
    
    def setXYLoc(self, x, y):
        self.x = x
        self.y = y