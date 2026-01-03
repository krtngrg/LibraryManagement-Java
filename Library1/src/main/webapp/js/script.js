// Library Management System - Custom JavaScript

// Document Ready
$(document).ready(function() {
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Auto-hide alerts after 5 seconds
    setTimeout(function() {
        $('.alert').fadeOut('slow');
    }, 5000);
    
    // Confirm delete actions
    $('.delete-btn').on('click', function(e) {
        if (!confirm('Are you sure you want to delete this item? This action cannot be undone.')) {
            e.preventDefault();
            return false;
        }
    });
    
    // Form validation enhancement
    $('form').on('submit', function() {
        $(this).find('button[type="submit"]').prop('disabled', true).html(
            '<span class="spinner-border spinner-border-sm me-2"></span>Processing...'
        );
    });
    
    // Search functionality
    $('#searchInput').on('keyup', function() {
        var value = $(this).val().toLowerCase();
        $('#dataTable tbody tr').filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });
    
    // Print functionality
    $('.print-btn').on('click', function() {
        window.print();
    });
    
    // Export to CSV functionality
    $('.export-csv-btn').on('click', function() {
        exportTableToCSV('data.csv');
    });
    
    // Add fade-in animation to cards
    $('.card').addClass('fade-in');
});

// Export table to CSV
function exportTableToCSV(filename) {
    var csv = [];
    var rows = document.querySelectorAll('table tr');
    
    for (var i = 0; i < rows.length; i++) {
        var row = [], cols = rows[i].querySelectorAll('td, th');
        
        for (var j = 0; j < cols.length; j++) {
            row.push(cols[j].innerText);
        }
        
        csv.push(row.join(','));
    }
    
    downloadCSV(csv.join('\n'), filename);
}

// Download CSV file
function downloadCSV(csv, filename) {
    var csvFile;
    var downloadLink;
    
    csvFile = new Blob([csv], {type: 'text/csv'});
    downloadLink = document.createElement('a');
    downloadLink.download = filename;
    downloadLink.href = window.URL.createObjectURL(csvFile);
    downloadLink.style.display = 'none';
    document.body.appendChild(downloadLink);
    downloadLink.click();
}

// Format currency
function formatCurrency(amount) {
    return '₹' + parseFloat(amount).toFixed(2);
}

// Calculate penalty
function calculatePenalty(daysLate) {
    var penaltyPerDay = 5;
    return daysLate * penaltyPerDay;
}

// Validate email
function validateEmail(email) {
    var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// Validate phone number
function validatePhone(phone) {
    var re = /^[0-9]{10}$/;
    return re.test(phone);
}

// Show loading spinner
function showLoading() {
    $('body').append('<div class="loading-overlay"><div class="spinner-border text-primary"></div></div>');
}

// Hide loading spinner
function hideLoading() {
    $('.loading-overlay').remove();
}

// Show success message
function showSuccess(message) {
    showAlert('success', message);
}

// Show error message
function showError(message) {
    showAlert('danger', message);
}

// Show alert message
function showAlert(type, message) {
    var alertHtml = '<div class="alert alert-' + type + ' alert-dismissible fade show" role="alert">' +
                    '<i class="fas fa-' + (type === 'success' ? 'check' : 'exclamation') + '-circle"></i> ' +
                    message +
                    '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>' +
                    '</div>';
    
    $('.container-fluid').prepend(alertHtml);
    
    setTimeout(function() {
        $('.alert').fadeOut('slow', function() {
            $(this).remove();
        });
    }, 5000);
}

// Confirm action
function confirmAction(message, callback) {
    if (confirm(message)) {
        callback();
    }
}

// Format date
function formatDate(dateString) {
    var date = new Date(dateString);
    var day = ('0' + date.getDate()).slice(-2);
    var month = ('0' + (date.getMonth() + 1)).slice(-2);
    var year = date.getFullYear();
    return day + '/' + month + '/' + year;
}

// Calculate days between dates
function daysBetween(date1, date2) {
    var oneDay = 24 * 60 * 60 * 1000;
    var firstDate = new Date(date1);
    var secondDate = new Date(date2);
    return Math.round(Math.abs((firstDate - secondDate) / oneDay));
}

// Check if date is overdue
function isOverdue(dueDate) {
    var today = new Date();
    var due = new Date(dueDate);
    return today > due;
}

// Initialize DataTables (if library is included)
function initDataTable(tableId) {
    if (typeof $.fn.DataTable !== 'undefined') {
        $('#' + tableId).DataTable({
            'pageLength': 10,
            'ordering': true,
            'searching': true,
            'responsive': true
        });
    }
}

// AJAX form submission
function submitFormAjax(formId, successCallback) {
    $('#' + formId).on('submit', function(e) {
        e.preventDefault();
        
        $.ajax({
            type: $(this).attr('method'),
            url: $(this).attr('action'),
            data: $(this).serialize(),
            success: function(response) {
                if (successCallback) {
                    successCallback(response);
                }
            },
            error: function(xhr, status, error) {
                showError('An error occurred: ' + error);
            }
        });
    });
}
