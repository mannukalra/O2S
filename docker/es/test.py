import sys
import os


''' Read input from STDIN. Print your output to STDOUT '''
# Use input() to read input from STDIN and use print to write your output to STDOUT
# Write code here

n = int(input())
weights = sorted([int(x) for x in input().split()])

target_count = {}
start, end = 0, len(weights) - 1
curr_target = weights[start] + weights[end]
min_target = curr_target - 1
max_target = curr_target + 1


def updateTargetCount(left, right, target) :
    global min_target, max_target, curr_target
    target_count[target] = 0
    while left < right:
        curr_sum = weights[left] + weights[right]
        if curr_sum < target:
            left += 1
            if target_count[curr_sum]:
                target_count[curr_sum] = 0
        elif curr_sum > target:
            right -= 1
            if target_count[curr_sum]:
                target_count[curr_sum] = 0
        else:
            target_count[target] = target_count.get(target, 0) + 1
            left += 1
            right -= 1



while not curr_target in target_count:
    updateTargetCount(start, end, curr_target)

print(target_count)