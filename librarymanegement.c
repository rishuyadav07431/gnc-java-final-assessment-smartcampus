#ifndef LIBRARY_H
#define LIBRARY_H

#include <time.h>

#define MAX_BOOKS 100
#define MAX_MEMBERS 100
#define MAX_TRANSACTIONS 500
#define MAX_TITLE 100
#define MAX_AUTHOR 50
#define MAX_ISBN 20
#define MAX_NAME 100
#define FINE_PER_DAY 5.0
#define BORROWING_DAYS 14

typedef enum { AVAILABLE, BORROWED, DAMAGED } BookStatus;
typedef enum { ACTIVE, INACTIVE } MemberStatus;
typedef enum { ISSUED, RETURNED, OVERDUE } TransactionStatus;

// Book Structure
typedef struct {
    int book_id;
    char title[MAX_TITLE];
    char author[MAX_AUTHOR];
    char isbn[MAX_ISBN];
    char category[50];
    int total_copies;
    int available_copies;
    float price;
    int publication_year;
    BookStatus status;
    char added_date[11];
} Book;

// Member Structure
typedef struct {
    int member_id;
    char name[MAX_NAME];
    char email[100];
    char phone[15];
    char address[200];
    int books_borrowed;
    float total_fine;
    MemberStatus status;
    char join_date[11];
} Member;

// Transaction Structure (for tracking issue/return)
typedef struct {
    int transaction_id;
    int book_id;
    int member_id;
    char issue_date[11];
    char due_date[11];
    char return_date[11];
    float fine_amount;
    TransactionStatus status;
} Transaction;

// ========== BOOK FUNCTIONS ==========
void add_book(Book books[], int *count);
void view_all_books(Book books[], int count);
void search_book_by_title(Book books[], int count);
void search_book_by_author(Book books[], int count);
void search_book_by_isbn(Book books[], int count);
void view_book_by_category(Book books[], int count);
void update_book(Book books[], int count);
void delete_book(Book books[], int *count);
void display_book_statistics(Book books[], int count);

// ========== MEMBER FUNCTIONS ==========
void add_member(Member members[], int *count);
void view_all_members(Member members[], int count);
void search_member_by_id(Member members[], int count);
void search_member_by_name(Member members[], int count);
void update_member(Member members[], int count);
void delete_member(Member members[], int *count);
void view_member_details(Member members[], int count);

// ========== TRANSACTION FUNCTIONS ==========
void issue_book(Book books[], Member members[], Transaction transactions[], 
                int book_count, int member_count, int *transaction_count);
void return_book(Book books[], Member members[], Transaction transactions[], 
                 int book_count, int member_count, int transaction_count);
void view_transaction_history(Transaction transactions[], int count);
void view_member_borrowed_books(Book books[], Member members[], 
                               Transaction transactions[], int book_count, 
                               int member_count, int transaction_count);

// ========== FINE FUNCTIONS ==========
void calculate_fine(Transaction transactions[], int transaction_count);
void view_member_fine(Member members[], Transaction transactions[], 
                      int member_count, int transaction_count);
void pay_fine(Member members[], Transaction transactions[], 
              int member_count, int transaction_count);

// ========== REPORT FUNCTIONS ==========
void generate_library_report(Book books[], Member members[], 
                            Transaction transactions[], int book_count, 
                            int member_count, int transaction_count);
void view_overdue_books(Transaction transactions[], int transaction_count);
void list_most_borrowed_books(Transaction transactions[], Book books[], 
                              int transaction_count, int book_count);

// ========== UTILITY FUNCTIONS ==========
void get_current_date(char *date_str);
int calculate_days_difference(const char *date1, const char *date2);
int is_valid_isbn(const char *isbn);
int is_valid_email(const char *email);
void trim_whitespace(char *str);
void clear_input_buffer();
int is_book_available(Book books[], int book_id);

// ========== FILE OPERATIONS ==========
void save_books_to_file(Book books[], int count);
void load_books_from_file(Book books[], int *count);
void save_members_to_file(Member members[], int count);
void load_members_from_file(Member members[], int *count);
void save_transactions_to_file(Transaction transactions[], int count);
void load_transactions_from_file(Transaction transactions[], int *count);

// ========== AUTHENTICATION ==========
int admin_login();
void display_welcome_screen();

#endif
