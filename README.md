# Grocery Store Till System - Developer Exercise

This exercise is designed to demonstrate a candidate's abilities across a range of competencies in software development. You may use any programming language and frameworks of your choice.

## Instructions
1. Fork the repository
2. Implement a solution that meets the requirements below
3. Share your forked repository with us

Feel free to use any helper packages/frameworks that you think will be beneficial.

## Business Requirements

### Overview
Create a grocery store till system that can scan items and calculate the total bill with support for promotional discounts. The currency is **aws** with 100 **clouds** (c) = 1 aws.

### Core Functionality

#### 1. Product Management
The till must support configuration of products with:
- Name (unique identifier)
- Price in clouds
- Category (e.g., "fruit", "vegetable", "bakery")

#### 2. Promotional Rules
Implement the following discount types:

**Bundle Discount (X for Y)**
- Customer buys X items from a specified list but only pays for Y of them
- The cheapest item(s) are free
- If more items than X are scanned, only the first X items found are eligible for the discount
- Multiple separate bundles can be applied if enough items exist

**Progressive Discount (Buy X Get Next at Y% off)**
- When buying multiple of the same item, receive progressive discounts
- Can be configured for any percentage off and any quantity threshold
- Example: "Buy 1 Get 1 at 50% off" or "Buy 2 Get 1 at 25% off"

**Bulk Purchase (X or more for Y clouds each)**
- When buying X or more of the same item, each item costs Y clouds instead of the regular price
- Example: "3 or more apples for 40c each" (regular price might be 50c)

#### 3. Discount Priority and Conflicts
When an item qualifies for multiple discounts:
- Each item can only be part of ONE discount
- The system should apply the discount that gives the customer the best value
- Document your logic for handling these scenarios

### Example Scenario

**Product Catalog:**
Product     Price    Category
apple       50c      fruit
banana      40c      fruit
tomato      30c      vegetable
potato      26c      vegetable
bread       120c     bakery
milk        95c      dairy

**Active Promotions:**
1. Bundle Discount: 3 for 2 on ["apple", "banana", "tomato"]
2. Progressive Discount: Buy 1 Get 1 at 50% off on "potato"
3. Bulk Purchase: 4 or more "bread" for 100c each

**Scanned Basket:**

["apple", "banana", "banana", "potato", "tomato", "banana", "potato"]

**Expected Calculation:**
1. **Bundle Discount (3 for 2):** "apple", "banana", "banana" → Pay for apple (50c) + banana (40c) = 90c *(one 40c banana is free)*
2. **Progressive Discount:** "potato", "potato" → 26c + 13c = 39c *(second potato is 50% off)*
3. **Regular Price:** "tomato" (30c) + "banana" (40c) = 70c
4. **Total:** 90c + 39c + 70c = **199c = 1 aws and 99 clouds**

---

## Technical Requirements

Your solution should demonstrate:

### 1. Till Operations
- Scan items one at a time or in batch
- Calculate running total
- Apply promotional discounts automatically
- Display itemized receipt showing:
  - Each scanned item and its price
  - Applied discounts with savings amount
  - Final total in aws and clouds

### 2. Configuration Interface
Administrators should be able to:
- Add/remove/update products
- Create/modify/remove promotional rules
- View current product catalog and active promotions

### 3. Edge Cases to Consider
- Scanning items not in the catalog
- Empty baskets
- Scanning the same item multiple times
- Multiple overlapping promotions
- Items that could qualify for multiple discount types

---

## Delivery Format

Choose **one** of the following formats for your solution:

1. **REST API** - With endpoints for configuration and checkout operations
2. **CLI Application** - Interactive command-line interface
3. **Web Application** - UI for both customer and administrator views

---

## Questions?

Feel free to make reasonable assumptions where requirements are ambiguous, but document them in your README. Good luck! 🛒