print(list(map(lambda x: x * x, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10])))
print(list(map(lambda x: x * x, range(1, 21))))

print(list(filter(lambda x: x % 2 == 0, range(1, 11))))
print(list(filter(lambda x: x % 2 == 0, range(1, 21))))

print(list(map(lambda x: x ** 2, filter(lambda x: x % 2 == 0, range(1, 11)))))
print(list(map(lambda x: x ** 2, filter(lambda x: x % 2 == 0, range(1, 21)))))

def find_longest_word(l):
    a = l[0]
    la = len(a)
    for element in l:
        if len(element) > la:
            la = len(element)
            a = element
    return a

def find_long_words(l, n):
    return list(filter(lambda x: len(x) > n, l))

dict = {"merry":"god", "christmas":"jul", "and":"och", "happy":"gott", "new":"nytt", "year":"ar"}
def translate(l):
    return list(map(lambda x: dict[x], l))

def turning_1(l):
    ll = []
    for element in l:
        l.append(len(element))
    return ll
def turning_2(l):
    return list(map(lambda x: len(x), l))
def turning_3(l):
    return [len(x) for x in l]
    
from functools import reduce
def max_in_list(l):
    return reduce(lambda x, y: x if x > y else y, l)