import Shape, math

class Circle(Shape.Shape):
    def __init__(self):
        Shape.Shape.__init__(self)
    
    def calcArea(self):
        self.r = math.sqrt(self.x * self.x + self.y * self.y)
        return math.pi * self.r * self.r