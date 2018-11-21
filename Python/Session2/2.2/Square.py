import Shape, math

class Square(Shape.Shape):
    def __init__(self):
        Shape.Shape.__init__(self)
        
    def calcArea(self):
        self.s = math.sqrt(self.x * self.x + self.y * self.y)
        return self.s * self.s