import Animal

class Cow(Animal.Animal):
    def __init__(self, name, color, weight):
        Animal.Animal.__init__(self, name, color, weight)
    
    def talk(self):
        print('Growl...')
        
    def __str__(self):
        return str(self.name + ' is a cow, ' + self.name + ' is also an animal. Its color is ' + self.color + ' and weight ' + str(self.weight) + 'kg.')