import Shape, math

class Triangle(Shape.Shape):
    def __init__(self):
        Shape.Shape.__init__(self)
        
    def calcArea(self):
        self.s = math.sqrt(self.x * self.x + self.y * self.y)
        return (self.s * self.x * math.sqrt(3)) / 4