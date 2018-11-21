import Animal

class Bird(Animal.Animal):
    def __init__(self, name, color, weight):
        Animal.Animal.__init__(self, name, color, weight)
    
    def talk(self):
        print('Chirp...')
        
    def __str__(self):
        return str(self.name + ' is a bird, ' + self.name + ' is also an animal. Its color is ' + self.color + ' and weight ' + str(self.weight) + 'kg.')